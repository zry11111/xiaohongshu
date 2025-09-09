package com.zry.xiaohongshu.search.model.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserReqVO {
    @NotBlank(message = "关键词不能为空")
    private String keyword;
    @Min(value = 1, message = "每页数量不能小于1")
    private Integer pageNo;
}
