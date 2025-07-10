package com.zry.xiaohongshu.auth.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zry.xiaohongshu.auth.alarn.AlarmInterface;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @NacosValue(value = "${rate-limit.api.limit}",autoRefreshed = true)
    private Integer limit;
    @Resource
    private AlarmInterface alarm;
    @GetMapping("/test")
    public String test() {
        return "当前限流阈值为: " + limit;
    }
    @GetMapping("/alarm")
    public String alarm() {
        alarm.sendMessage("测试告警信息");
        return "alarm success";
    }
}
