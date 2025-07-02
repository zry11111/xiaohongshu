package com.zry.xiaohongshu.auth;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("testKey", "testValue");
    }
}
