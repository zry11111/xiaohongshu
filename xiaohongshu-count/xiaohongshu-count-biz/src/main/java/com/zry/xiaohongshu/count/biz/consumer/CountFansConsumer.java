package com.zry.xiaohongshu.count.biz.consumer;

import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.Maps;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.count.biz.constant.MQConstants;
import com.zry.xiaohongshu.count.biz.constant.RedisKeyConstants;
import com.zry.xiaohongshu.count.biz.enums.FollowUnfollowTypeEnum;
import com.zry.xiaohongshu.count.biz.model.dto.CountFollowUnfollowMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RocketMQMessageListener(consumerGroup = "xiaohongshu_group_"+MQConstants.TOPIC_COUNT_FANS,
                    topic = MQConstants.TOPIC_COUNT_FANS)
@Slf4j
public class CountFansConsumer implements RocketMQListener<String> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private BufferTrigger<String> bufferTrigger = BufferTrigger.<String>batchBlocking()
            .bufferSize(50000) // 缓存队列的最大容量
            .batchSize(1000)   // 一批次最多聚合 1000 条
            .linger(Duration.ofSeconds(1)) // 多久聚合一次
            .setConsumerEx(this::consumeMessage)
            .build();
    @Override
    public void onMessage(String body) {
        log.info("## 消费到了 MQ 【计数: 粉丝数】, {}...", body);
        // 往 bufferTrigger 中添加元素
        bufferTrigger.enqueue(body);
    }
    private void consumeMessage(List<String> bodys) {
        log.info("==> 聚合消息, size: {}", bodys.size());
        log.info("==> 聚合消息, {}", JsonUtils.toJsonString(bodys));
        List<CountFollowUnfollowMqDTO> countFollowUnfollowMqDTOS = bodys.stream()
                .map(body -> JsonUtils.parseObject(body, CountFollowUnfollowMqDTO.class)).toList();
        Map<Long, List<CountFollowUnfollowMqDTO>> groupMap = countFollowUnfollowMqDTOS.stream()
                .collect(Collectors.groupingBy(CountFollowUnfollowMqDTO::getTargetUserId));
        HashMap<Long, Integer> countMap = Maps.newHashMap();
        for (Map.Entry<Long,List<CountFollowUnfollowMqDTO>> entry : groupMap.entrySet()) {
            List<CountFollowUnfollowMqDTO> list = entry.getValue();
            int finalCount = 0;
            for (CountFollowUnfollowMqDTO countFollowUnfollowMqDTO: list) {
                Integer type = countFollowUnfollowMqDTO.getType();
                FollowUnfollowTypeEnum followUnfollowTypeEnum = FollowUnfollowTypeEnum.valueOf(type);
                if(Objects.isNull(followUnfollowTypeEnum)){
                    log.warn("==> 未知的关注取关类型: {}", type);
                    continue;
                }
                switch (followUnfollowTypeEnum) {
                    case FOLLOW -> finalCount += 1;
                    case UNFOLLOW -> finalCount -= 1;
                }
            }
            countMap.put(entry.getKey(),finalCount);
        }
        log.info("## 聚合后的计数数据: {}", JsonUtils.toJsonString(countMap));

        //更新Redis数据
        countMap.forEach((k,v)->{
            String redisKey = RedisKeyConstants.buildCountUserKey(k);
            Boolean isExisted = redisTemplate.hasKey(redisKey);

            if(isExisted){
                redisTemplate.opsForHash().increment(redisKey, RedisKeyConstants.FIELD_FANS_TOTAL, v);
            }
        });
        //发送MQ，通知数据落库

    }
}
