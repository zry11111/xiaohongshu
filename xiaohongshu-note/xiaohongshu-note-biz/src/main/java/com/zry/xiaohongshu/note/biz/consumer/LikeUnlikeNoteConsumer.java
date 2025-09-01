package com.zry.xiaohongshu.note.biz.consumer;

import com.alibaba.nacos.shaded.com.google.common.util.concurrent.RateLimiter;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.note.biz.constant.MQConstants;
import com.zry.xiaohongshu.note.biz.domain.dataobject.NoteLikeDO;
import com.zry.xiaohongshu.note.biz.domain.mapper.NoteDOMapper;
import com.zry.xiaohongshu.note.biz.domain.mapper.NoteLikeDOMapper;
import com.zry.xiaohongshu.note.biz.model.dto.LikeUnlikeNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

import java.time.LocalDateTime;
import java.util.Objects;

@RocketMQMessageListener(consumerGroup = "xiaohongshu_group_"+ MQConstants.TOPIC_LIKE_OR_UNLIKE,
        topic = "LikeUnlikeTopic")
@Slf4j
public class LikeUnlikeNoteConsumer implements RocketMQListener<Message> {
    @Resource
    private NoteLikeDOMapper noteLikeDOMapper;
    RateLimiter rateLimiter =  RateLimiter.create(5000);
    @Override
    public void onMessage(Message message) {
        rateLimiter.acquire();
        String bodyJsonStr = new String(message.getBody());
        String tags = message.getTags();
        log.info("## 点赞、取消点赞消费, tags: {}, body: {}", tags, bodyJsonStr);
        if(Objects.equals(tags, MQConstants.TAG_LIKE)) {
            // 点赞
            handleLikeNoteTagMessage(bodyJsonStr);
        } else if(Objects.equals(tags, MQConstants.TAG_UNLIKE)) {
            // 取消点赞
        } else {
            handleUnlikeNoteTagMessage(bodyJsonStr);
        }

    }
    /**
     * 笔记点赞
     * @param bodyJsonStr
     */
    private void handleLikeNoteTagMessage(String bodyJsonStr) {
        LikeUnlikeNoteMqDTO likeNoteMqDTO = JsonUtils.parseObject(bodyJsonStr, LikeUnlikeNoteMqDTO.class);
        if(Objects.isNull(likeNoteMqDTO)) {
            log.error("## 点赞消费失败，参数转换失败, body: {}", bodyJsonStr);
            return;
        }
        // 用户ID
        Long userId = likeNoteMqDTO.getUserId();
        // 点赞的笔记ID
        Long noteId = likeNoteMqDTO.getNoteId();
        // 操作类型
        Integer type = likeNoteMqDTO.getType();
        // 点赞时间
        LocalDateTime createTime = likeNoteMqDTO.getCreateTime();

        // 构建 DO 对象
        NoteLikeDO noteLikeDO = NoteLikeDO.builder()
                .userId(userId)
                .noteId(noteId)
                .createTime(createTime)
                .status(type)
                .build();

        // 添加或更新笔记点赞记录
        int count = noteLikeDOMapper.insertOrUpdate(noteLikeDO);

        // TODO: 发送计数 MQ
    }

    /**
     * 笔记取消点赞
     * @param bodyJsonStr
     */
    private void handleUnlikeNoteTagMessage(String bodyJsonStr) {
    }
}
