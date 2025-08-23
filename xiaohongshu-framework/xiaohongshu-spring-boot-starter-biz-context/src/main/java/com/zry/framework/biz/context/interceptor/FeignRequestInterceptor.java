package com.zry.framework.biz.context.interceptor;

import com.zry.framework.biz.context.holder.LoginUserContextHolder;
import com.zry.framework.common.constant.GlobalConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = LoginUserContextHolder.getUserId();

        if(Objects.nonNull(userId)){
            requestTemplate.header(GlobalConstants.USER_ID,String.valueOf(userId));
            log.info("==> feign请求设置请求头 userId: {}",userId);
        }
    }
}
