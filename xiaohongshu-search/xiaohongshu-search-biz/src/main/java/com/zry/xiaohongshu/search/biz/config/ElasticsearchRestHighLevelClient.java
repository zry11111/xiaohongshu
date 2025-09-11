package com.zry.xiaohongshu.search.biz.config;

import jakarta.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestHighLevelClient {
    @Resource
    private ElasticsearchProperties elasticsearchProperties;
    private final String COLON = ":";
    private final String HTTP = "http";
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        String address = elasticsearchProperties.getAddress();
        String[] addresses = address.split(COLON);
        String host = addresses[0];
        int port = Integer.valueOf(addresses[1]);
        HttpHost httpHost = new HttpHost(host, port, HTTP);
        return  new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
