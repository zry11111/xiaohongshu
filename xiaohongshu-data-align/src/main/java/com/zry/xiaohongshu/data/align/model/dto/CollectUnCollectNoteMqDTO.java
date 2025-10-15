package com.zry.xiaohongshu.data.align.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectUnCollectNoteMqDTO {

    private Long userId;

    private Long noteId;

    /**
     * 0: 取消收藏， 1：收藏
     */
    private Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime createTime;

    /**
     * 笔记发布者 ID
     */
    private Long noteCreatorId;
}
