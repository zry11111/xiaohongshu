package com.zry.xiaohongshu.auth.alarn;

import com.zry.xiaohongshu.auth.alarn.impl.MailAlarmHelper;
import com.zry.xiaohongshu.auth.alarn.impl.SmsAlarmHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class AlarmConfig {
    @Value("${alarm.type}")
    private String alarmType;
    @Bean
    @RefreshScope
    public AlarmInterface alarmHelper(){
        return StringUtils.equals("sms", alarmType) ? new SmsAlarmHelper() : new MailAlarmHelper();
    }
}
