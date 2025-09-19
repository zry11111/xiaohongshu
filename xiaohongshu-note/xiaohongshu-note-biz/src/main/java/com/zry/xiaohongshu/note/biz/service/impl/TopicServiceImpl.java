package com.zry.xiaohongshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.note.biz.domain.dataobject.TopicDO;
import com.zry.xiaohongshu.note.biz.domain.mapper.TopicDOMapper;
import com.zry.xiaohongshu.note.biz.model.vo.FindTopicListReqVO;
import com.zry.xiaohongshu.note.biz.model.vo.FindTopicRspVO;
import com.zry.xiaohongshu.note.biz.service.TopicService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicDOMapper topicDOMapper;

    @Override
    public Response<List<FindTopicRspVO>> findTopicList(FindTopicListReqVO findTopicListReqVO) {
        String keyword = findTopicListReqVO.getKeyword();

        List<TopicDO> topicDOS = topicDOMapper.selectByLikeName(keyword);

        List<FindTopicRspVO> findTopicRspVOS = null;
        if (CollUtil.isNotEmpty(topicDOS)) {
            findTopicRspVOS = topicDOS.stream()
                    .map(topicDO -> FindTopicRspVO.builder()
                            .id(topicDO.getId())
                            .name(topicDO.getName())
                            .build())
                    .toList();
        }

        return Response.success(findTopicRspVOS);
    }
}
