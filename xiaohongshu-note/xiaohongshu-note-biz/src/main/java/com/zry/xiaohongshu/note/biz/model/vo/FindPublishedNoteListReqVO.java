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
public class FindPublishedNoteListReqVO {

    @NotNull(message = "用户 ID 不能为空")
    private Long userId;
//    状态(0：待审核 1：正常展示 2：被删除(逻辑删除) 3：被下架)
//    private Integer type = 0;
    /**
     * 游标，即笔记 ID，用于分页使用
     */
    private Long cursor;

}