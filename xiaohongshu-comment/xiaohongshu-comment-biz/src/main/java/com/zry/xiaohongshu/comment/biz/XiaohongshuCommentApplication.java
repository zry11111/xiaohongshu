package com.zry.xiaohongshu.comment.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
@MapperScan("com.zry.xiaohongshu.comment.biz.domain.mapper")
public class XiaohongshuCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuCommentApplication.class, args);
    }
}
