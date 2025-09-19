package com.zry.xiaohongshu.note.biz.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindTopicListReqVO {
    @NotBlank(message = "话题关键词不能为空")
    private String keyword;
}
