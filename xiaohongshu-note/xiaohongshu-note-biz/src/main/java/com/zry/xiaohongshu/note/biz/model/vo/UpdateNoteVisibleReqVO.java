package com.zry.xiaohongshu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNoteVisibleReqVO {

    @NotNull(message = "笔记 ID 不能为空")
    private Long id;
    //设置范围大小
    @NotNull(message = "visible 不能为空")
    private Integer visible;
}