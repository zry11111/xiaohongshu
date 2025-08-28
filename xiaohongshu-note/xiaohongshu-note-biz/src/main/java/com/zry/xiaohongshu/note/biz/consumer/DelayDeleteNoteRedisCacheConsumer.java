package com.zry.xiaohongshu.note.biz.consumer;

import com.zry.xiaohongshu.note.biz.constant.MQConstants;
import com.zry.xiaohongshu.note.biz.constant.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "xiaohongshu_group_"+ MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE,
                    topic = MQConstants.TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE)
public class DelayDeleteNoteRedisCacheConsumer implements RocketMQListener<String> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void onMessage(String body) {
        Long noteId = Long.valueOf(body);
        log.info("## 延迟删除笔记 Redis 缓存, noteId: {}", noteId);

        // 删除笔记缓存
        String key = RedisKeyConstants.buildNoteDetailKey(noteId);
        redisTemplate.delete(key);
    }
}
