package com.zry.xiaohongshu.comment.biz.consumer;

import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.comment.biz.constant.MQConstants;
import com.zry.xiaohongshu.comment.biz.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "xiaohongshu_group_"+ MQConstants.TOPIC_DELETE_COMMENT_LOCAL_CACHE
                    ,topic = MQConstants.TOPIC_DELETE_COMMENT_LOCAL_CACHE
                    ,messageModel = MessageModel.BROADCASTING)//广播模式
@Slf4j
public class DeleteCommentLocalCacheConsumer implements RocketMQListener<String> {
    @Resource
    private CommentService commentService;
    @Override
    public void onMessage(String body) {
        Long commentId = Long.valueOf(body);
        log.info("##消费成功,commentId:{}",commentId);
        commentService.deleteCommentLocalCache(commentId);

    }
}
