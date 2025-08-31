package com.zry.xiaohongshu.count.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zry.xiaohongshu.count.biz.domain.mapper")
public class XiaohongshuCountBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuCountBizApplication.class, args);
    }
}
