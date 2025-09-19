package com.zry.xiaohongshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ProfileNoteTypeEnum {

    ALL(1), // 所有笔记
    COLLECTED(2), // 收藏
    LIKED(3), // 点赞
    ;
    private final Integer code;

    public static ProfileNoteTypeEnum valueOf(Integer code) {
        for (ProfileNoteTypeEnum profileNoteTypeEnum : ProfileNoteTypeEnum.values()) {
            if (Objects.equals(code, profileNoteTypeEnum.getCode())) {
                return profileNoteTypeEnum;
            }
        }
        throw new IllegalArgumentException("错误的笔记列表查询类型");
    }

}
