package com.zry.xiaohongshu.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@MapperScan("com.zry.xiaohongshu.user.relation.biz.domain.mapper")
@EnableFeignClients("com.zry.xiaohongshu")
public class XiaohongshuUserRelationBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuUserRelationBizApplication.class, args);
    }
}
