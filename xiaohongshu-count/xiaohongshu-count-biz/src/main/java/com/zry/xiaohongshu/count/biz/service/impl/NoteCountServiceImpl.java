package com.zry.xiaohongshu.count.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Maps;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.biz.constant.RedisKeyConstants;
import com.zry.xiaohongshu.count.biz.domain.dataobject.NoteCountDO;
import com.zry.xiaohongshu.count.biz.domain.mapper.NoteCountDOMapper;
import com.zry.xiaohongshu.count.biz.service.NoteCountService;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdRspDTO;
import com.zry.xiaohongshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteCountServiceImpl implements NoteCountService {
    @Resource
    private NoteCountDOMapper noteCountDOMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO) {
        //从缓存中查
        List<Long> noteIds = findNoteCountsByIdsReqDTO.getNoteIds();
        List<String> hashKeys = noteIds.stream()
                .map(RedisKeyConstants::buildCountNoteKey)
                .toList();
        List<Object> countHashes = getCountHashesByPipelineFromRedis(hashKeys);
        List<FindNoteCountsByIdRspDTO> findNoteCountsByIdRspDTOS  = Lists.newArrayList();
        //未查找到数据的id
        List<Long> missNoteIds = Lists.newArrayList();
        for(int i = 0;i<noteIds.size();i++){
            Long noteId = noteIds.get(i);
            List<Integer> currCountHash = (List<Integer>) countHashes.get(i);
            //获取计数数据
            Integer likeTotal = currCountHash.get(0);
            Integer collectTotal = currCountHash.get(1);
            Integer commentTotal = currCountHash.get(2);
            if (Objects.isNull(likeTotal) || Objects.isNull(collectTotal) || Objects.isNull(commentTotal)) {
                missNoteIds.add(noteId);
            }

            // 构建 DTO
            FindNoteCountsByIdRspDTO findNoteCountsByIdRspDTO = FindNoteCountsByIdRspDTO.builder()
                    .noteId(noteId)
                    .likeTotal(Objects.nonNull(likeTotal) ? Long.valueOf(likeTotal) : null)
                    .collectTotal(Objects.nonNull(collectTotal) ? Long.valueOf(collectTotal) : null)
                    .commentTotal(Objects.nonNull(commentTotal) ? Long.valueOf(commentTotal) : null)
                    .build();

            findNoteCountsByIdRspDTOS.add(findNoteCountsByIdRspDTO);
        }
        if(CollUtil.isEmpty(missNoteIds)){
            return Response.success(findNoteCountsByIdRspDTOS);
        }
        //从数据库查
        List<NoteCountDO> noteCountDOS = noteCountDOMapper.selectByNoteIds(missNoteIds);
        if(CollUtil.isNotEmpty(noteCountDOS)){
            // DO 集合转 Map, 方便查询对应笔记 ID 的计数
            Map<Long, NoteCountDO> noteIdAndDOMap = noteCountDOS.stream()
                    .collect(Collectors.toMap(NoteCountDO::getNoteId, noteCountDO -> noteCountDO));

            // 将笔记 Hash 计数同步到 Redis 中
            syncNoteHash2Redis(findNoteCountsByIdRspDTOS, noteIdAndDOMap);

            // 针对 DTO 中为 null 的计数字段，循环设置从数据库中查询到的计数
            for (FindNoteCountsByIdRspDTO findNoteCountsByIdRspDTO : findNoteCountsByIdRspDTOS) {
                Long noteId = findNoteCountsByIdRspDTO.getNoteId();
                Long likeTotal = findNoteCountsByIdRspDTO.getLikeTotal();
                Long collectTotal = findNoteCountsByIdRspDTO.getCollectTotal();
                Long commentTotal = findNoteCountsByIdRspDTO.getCommentTotal();

                NoteCountDO noteCountDO = noteIdAndDOMap.get(noteId);

                if (Objects.isNull(likeTotal))
                    findNoteCountsByIdRspDTO.setLikeTotal(Objects.nonNull(noteCountDO) ? noteCountDO.getLikeTotal() : 0);
                if (Objects.isNull(collectTotal))
                    findNoteCountsByIdRspDTO.setCollectTotal(Objects.nonNull(noteCountDO) ? noteCountDO.getCollectTotal() : 0);
                if (Objects.isNull(commentTotal))
                    findNoteCountsByIdRspDTO.setCommentTotal(Objects.nonNull(noteCountDO) ? noteCountDO.getCommentTotal() : 0);
            }
        }
        return Response.success(findNoteCountsByIdRspDTOS);
    }

    private void syncNoteHash2Redis(List<FindNoteCountsByIdRspDTO> findNoteCountsByIdRspDTOS, Map<Long, NoteCountDO> noteIdAndDOMap) {
        // 将笔记计数同步到 Redis 中
        redisTemplate.executePipelined(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) {
                // 循环已构建好的返参 DTO 集合
                for (FindNoteCountsByIdRspDTO findNoteCountsByIdRspDTO : findNoteCountsByIdRspDTOS) {
                    Long likeTotal = findNoteCountsByIdRspDTO.getLikeTotal();
                    Long collectTotal = findNoteCountsByIdRspDTO.getCollectTotal();
                    Long commentTotal = findNoteCountsByIdRspDTO.getCommentTotal();

                    // 若当前 DTO 的所有计数都不为空，则无需同步 Hash
                    if (Objects.nonNull(likeTotal) && Objects.nonNull(collectTotal) && Objects.nonNull(commentTotal)) {
                        continue;
                    }

                    // 否则，若有任意一个 Field 计数为空，则需要同步对应的 Field
                    Long noteId = findNoteCountsByIdRspDTO.getNoteId();
                    // 构建 Hash Key
                    String noteCountHashKey = RedisKeyConstants.buildCountNoteKey(noteId);

                    // 设置 Field 计数
                    Map<String, Long> countMap = Maps.newHashMap();
                    NoteCountDO noteCountDO = noteIdAndDOMap.get(noteId);

                    if (Objects.isNull(likeTotal)) {
                        countMap.put(RedisKeyConstants.FIELD_LIKE_TOTAL,
                                Objects.nonNull(noteCountDO) ? noteCountDO.getLikeTotal() : 0);
                    }
                    if (Objects.isNull(collectTotal)) {
                        countMap.put(RedisKeyConstants.FIELD_COLLECT_TOTAL,
                                Objects.nonNull(noteCountDO) ? noteCountDO.getCollectTotal() : 0);
                    }
                    if (Objects.isNull(commentTotal)) {
                        countMap.put(RedisKeyConstants.FIELD_COMMENT_TOTAL,
                                Objects.nonNull(noteCountDO) ? noteCountDO.getCommentTotal() : 0);
                    }

                    operations.opsForHash().putAll(noteCountHashKey, countMap);

                    long expireTime = 60 * 30 + RandomUtil.randomInt(60 * 30);
                    operations.expire(noteCountHashKey, expireTime, TimeUnit.SECONDS);
                }

                return null;
            }
        });
    }

    private List<Object> getCountHashesByPipelineFromRedis(List<String> hashKeys) {
        //使用Pipeline批量查询
        return redisTemplate.executePipelined(new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations){
                for(String hashKey : hashKeys){
                    operations.opsForHash().multiGet(hashKey, List.of(
                            RedisKeyConstants.FIELD_LIKE_TOTAL,
                            RedisKeyConstants.FIELD_COLLECT_TOTAL,
                            RedisKeyConstants.FIELD_COMMENT_TOTAL
                    ));
                }
                return null;
            }
        });
    }
}
