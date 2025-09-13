package com.zry.xiaohongshu.comment.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

import javax.swing.*;

@SpringBootApplication
@EnableRetry
@EnableFeignClients(basePackages = "com.zry.xiaohongshu")
@MapperScan("com.zry.xiaohongshu.comment.biz.domain.mapper")
public class XiaohongshuCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuCommentApplication.class, args);
    }
}
