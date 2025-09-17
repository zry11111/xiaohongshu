package com.zry.xiaohongshu.user.biz;

import com.zry.xiaohongshu.count.api.CountFeignApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class FeignClientTest {

    @Autowired(required = false) // 设置为 required = false 避免启动失败
    private CountFeignApi countFeignApi;

    @Test
    public void testFeignClientExists() {
        assertNotNull("CountFeignApi should be available", countFeignApi);
    }
}