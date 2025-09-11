package com.zry.xiaohongshu.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.zry.xiaohongshu.search.domain.mapper")
public class XiaohongshuSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuSearchApplication.class, args);
    }
}
