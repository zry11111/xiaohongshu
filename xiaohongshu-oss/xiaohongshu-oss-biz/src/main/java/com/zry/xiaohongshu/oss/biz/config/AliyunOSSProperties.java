package com.zry.xiaohongshu.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "storage.aliyun-oss")

public class AliyunOSSProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
