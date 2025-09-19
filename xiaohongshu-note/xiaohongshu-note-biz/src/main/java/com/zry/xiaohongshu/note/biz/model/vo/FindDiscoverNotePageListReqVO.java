package com.zry.xiaohongshu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindDiscoverNotePageListReqVO {

    private Long channelId;

    @NotNull(message = "页码不能为空")
    private Integer pageNo = 1;

}
