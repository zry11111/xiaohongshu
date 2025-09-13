package com.zry.xiaohongshu.comment.biz.service.impl;

import com.google.common.base.Preconditions;
import com.zry.framework.biz.context.holder.LoginUserContextHolder;
import com.zry.framework.common.reponse.Response;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.comment.biz.constant.MQConstants;
import com.zry.xiaohongshu.comment.biz.model.dto.PublishCommentMqDTO;
import com.zry.xiaohongshu.comment.biz.model.vo.PublishCommentReqVO;
import com.zry.xiaohongshu.comment.biz.retry.SendMqRetryHelper;
import com.zry.xiaohongshu.comment.biz.rpc.DistributedIdGeneratorRpcService;
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
    private SendMqRetryHelper sendMqRetryHelper;
    @Resource
    private DistributedIdGeneratorRpcService distributedIdGeneratorRpcService;
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
        String commentId = distributedIdGeneratorRpcService.generateCommentId();
        //构建消息体
        PublishCommentMqDTO publishCommentMqDTO = PublishCommentMqDTO.builder()
                .noteId(publishCommentReqVO.getNoteId())
                .content(content)
                .imageUrl(imageUrl)
                .commentId(Long.valueOf(commentId))
                .replyCommentId(publishCommentReqVO.getReplyCommentId())
                .createTime(LocalDateTime.now())
                .creatorId(creatorId)
                .build();
        // 发送消息到 MQ
        sendMqRetryHelper.asyncSend(MQConstants.TOPIC_PUBLISH_COMMENT,JsonUtils.toJsonString(publishCommentMqDTO));

        return Response.success();
    }
}
