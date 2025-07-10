package com.zry.xiaohongshu.auth.alarn.impl;

import com.zry.xiaohongshu.auth.alarn.AlarmInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsAlarmHelper implements AlarmInterface {
    @Override
    public boolean sendMessage(String message) {
        log.info("==>短信告警：{}", message);
        return true;
    }
}
