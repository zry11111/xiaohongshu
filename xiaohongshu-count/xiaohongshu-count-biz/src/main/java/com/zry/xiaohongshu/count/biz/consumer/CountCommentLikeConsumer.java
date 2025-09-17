package com.zry.xiaohongshu.count.biz.consumer;

import com.github.phantomthief.collection.BufferTrigger;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.count.biz.constant.MQConstants;
import com.zry.xiaohongshu.count.biz.constant.RedisKeyConstants;
import com.zry.xiaohongshu.count.biz.enums.LikeUnlikeCommentTypeEnum;
import com.zry.xiaohongshu.count.biz.model.dto.AggregationCountLikeUnlikeCommentMqDTO;
import com.zry.xiaohongshu.count.biz.model.dto.CountLikeUnlikeCommentMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
@RocketMQMessageListener(consumerGroup = "xiaohongshu_group_count_"+ MQConstants.TOPIC_COMMENT_LIKE_OR_UNLIKE
            ,topic = MQConstants.TOPIC_COMMENT_LIKE_OR_UNLIKE)
@Slf4j
public class CountCommentLikeConsumer implements RocketMQListener<String> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    private BufferTrigger<String> bufferTrigger = BufferTrigger.<String>batchBlocking()
            .bufferSize(50000)
            .batchSize(1000)
            .linger(Duration.ofSeconds(1))
            .setConsumerEx(this::consumeMessages)
            .build();

    @Override
    public void onMessage(String body) {
        bufferTrigger.enqueue(body);
    }

    private void consumeMessages(List<String> bodys) {
        // 消息聚合后的处理逻辑
        log.info("CountCommentLikeConsumer 消费消息大小: {}", bodys.size());
        List<CountLikeUnlikeCommentMqDTO> countLikeUnlikeCommentMqDTOS = bodys.stream()
                .map(body -> JsonUtils.parseObject(body, CountLikeUnlikeCommentMqDTO.class)).toList();
        //按照评论id对数据进行分组
        Map<Long, List<CountLikeUnlikeCommentMqDTO>> groupMap = countLikeUnlikeCommentMqDTOS.stream()
                .collect(Collectors.groupingBy(CountLikeUnlikeCommentMqDTO::getCommentId));

        List<AggregationCountLikeUnlikeCommentMqDTO> countList = Lists.newArrayList();
        for (Map.Entry<Long, List<CountLikeUnlikeCommentMqDTO>> entry : groupMap.entrySet()) {
            // 评论 ID
            Long commentId = entry.getKey();

            List<CountLikeUnlikeCommentMqDTO> list = entry.getValue();
            // 最终的计数值，默认为 0
            int finalCount = 0;
            for (CountLikeUnlikeCommentMqDTO countLikeUnlikeCommentMqDTO : list) {
                // 获取操作类型
                Integer type = countLikeUnlikeCommentMqDTO.getType();

                // 根据操作类型，获取对应枚举
                LikeUnlikeCommentTypeEnum likeUnlikeCommentTypeEnum = LikeUnlikeCommentTypeEnum.valueOf(type);

                // 若枚举为空，跳到下一次循环
                if (Objects.isNull(likeUnlikeCommentTypeEnum)) continue;

                switch (likeUnlikeCommentTypeEnum) {
                    case LIKE -> finalCount += 1; // 如果为点赞操作，点赞数 +1
                    case UNLIKE -> finalCount -= 1; // 如果为取消点赞操作，点赞数 -1
                }
            }
            // 将分组后统计出的最终计数，存入 countList 中
            countList.add(AggregationCountLikeUnlikeCommentMqDTO.builder()
                    .commentId(commentId)
                    .count(finalCount)
                    .build());
        }

        log.info("## 【评论点赞数】聚合后的计数数据: {}", JsonUtils.toJsonString(countList));
        countList.forEach(item -> {
            Long commentId = item.getCommentId();
            Integer count = item.getCount();
            //构建key
            String countCommentKey = RedisKeyConstants.buildCountCommentKey(commentId);
            //判断该key是否存在
            Boolean hasKey = redisTemplate.hasKey(countCommentKey);
            if(hasKey){
                //存在则进行增量更新
                redisTemplate.opsForHash().increment(countCommentKey,RedisKeyConstants.FIELD_LIKE_TOTAL,count);
            }
        });
        // 发送聚合后的结果到下一个处理步骤的消息队列
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(countList))
                .build();
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_COMMENT_LIKE_2_DB, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("## 【评论点赞数】聚合结果发送成功: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("## 【评论点赞数】聚合结果发送失败: {}", throwable.getMessage(), throwable);
            }
        });
    }
}
