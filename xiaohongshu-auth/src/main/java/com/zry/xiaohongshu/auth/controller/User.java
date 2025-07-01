package com.zry.xiaohongshu.auth.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NotBlank(message = "用户名不能为空")
    private String nickName;
    private LocalDateTime createTime;
}
