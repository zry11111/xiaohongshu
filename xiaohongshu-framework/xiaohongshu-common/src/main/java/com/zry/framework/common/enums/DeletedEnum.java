package com.zry.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeletedEnum {
    YES(true),
    NO(false);

    private final boolean value;
}
