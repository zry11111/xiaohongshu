package com.zry.xiaohongshu.gateway.enums;

import com.zry.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum implements BaseExceptionInterface {
    SYSTEM_ERROR("500","系统繁忙，请稍后再试"),
    UNAUTHORIZED("401","权限不足"),;
    private final String errorCode;
    private final String errorMessage;
}
