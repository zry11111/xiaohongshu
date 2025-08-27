package com.zry.xiaohongshu.kv.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddNoteContentReqDTO {
    @NotBlank(message = "笔记ID不能为空")
    private String uuid;
    @NotBlank(message = "笔记内容不能为空")
    //空格也是不为null，同时不允许发送空白的笔记内容
    private String content;
}
