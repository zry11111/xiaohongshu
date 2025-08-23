package com.zry.xiaohongshu.user.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexEnum {
    WOMAN(0),
    MAN(1),
    ;
    private final Integer value;
    public static boolean isValid(Integer value){
        for(SexEnum loginTypeEnum : SexEnum.values()){
            if(loginTypeEnum.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
}
