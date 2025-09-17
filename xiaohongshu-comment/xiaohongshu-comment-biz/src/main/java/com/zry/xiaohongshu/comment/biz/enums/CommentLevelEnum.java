package com.zry.xiaohongshu.comment.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CommentLevelEnum {
    // 一级评论
    ONE(1),
    // 二级评论
    TWO(2),
    ;
    private final Integer code;
    public static CommentLevelEnum valueOf(Integer code) {
        for (CommentLevelEnum commentLevelEnum : CommentLevelEnum.values()) {
            if (Objects.equals(code, commentLevelEnum.getCode())) {
                return commentLevelEnum;
            }
        }
        return null;
    }
}