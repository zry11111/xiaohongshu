package com.zry.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    ENABLED(0),
    DISABLED(1);

    private final Integer value;
}
