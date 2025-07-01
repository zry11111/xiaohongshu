package com.zry.framework.biz.operationlog.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApiOperationLog {
    String description() default ""; // 接口描述
}
