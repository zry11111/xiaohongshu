package com.zry.xiaohongshu.comment.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = RetryProperties.PREFIX)
@Component
@Data
public class RetryProperties {
    public static final String PREFIX = "retry";
    private Integer maxAttempts = 3;
    private Integer initInterval = 1000;
    private Double multiplier = 2.0;
}
