package com.zry.xiaohongshu.data.align;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zry.xiaohongshu.data.align.domain.mapper")
@EnableFeignClients(basePackages = "com.zry.xiaohongshu")
public class XiaohongshuDataAlignApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuDataAlignApplication.class, args);
    }
}
