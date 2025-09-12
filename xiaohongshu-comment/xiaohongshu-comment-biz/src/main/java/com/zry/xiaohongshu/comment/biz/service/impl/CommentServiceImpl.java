package com.zry.xiaohongshu.comment.biz.service.impl;

import com.google.common.base.Preconditions;
import com.zry.framework.biz.context.holder.LoginUserContextHolder;
import com.zry.framework.common.reponse.Response;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.comment.biz.constant.MQConstants;
import com.zry.xiaohongshu.comment.biz.model.dto.PublishCommentMqDTO;
import com.zry.xiaohongshu.comment.biz.model.vo.PublishCommentReqVO;
import com.zry.xiaohongshu.comment.biz.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public Response<?> publishComment(PublishCommentReqVO publishCommentReqVO) {
        // 评论正文
        String content = publishCommentReqVO.getContent();
        // 附近图片
        String imageUrl = publishCommentReqVO.getImageUrl();

        // 评论内容和图片不能同时为空
        Preconditions.checkArgument(StringUtils.isNotBlank(content) || StringUtils.isNotBlank(imageUrl),
                "评论正文和图片不能同时为空");

        //
        Long creatorId = LoginUserContextHolder.getUserId();
        //构建消息体
        PublishCommentMqDTO publishCommentMqDTO = PublishCommentMqDTO.builder()
                .noteId(publishCommentReqVO.getNoteId())
                .content(content)
                .imageUrl(imageUrl)
                .replyCommentId(publishCommentReqVO.getReplyCommentId())
                .createTime(LocalDateTime.now())
                .creatorId(creatorId)
                .build();
        // 发送消息到 MQ
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(publishCommentMqDTO)).build();
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_PUBLISH_COMMENT, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【评论发布】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【评论发布】MQ 发送异常: ", throwable);
            }
        });

        return Response.success();
    }
}
