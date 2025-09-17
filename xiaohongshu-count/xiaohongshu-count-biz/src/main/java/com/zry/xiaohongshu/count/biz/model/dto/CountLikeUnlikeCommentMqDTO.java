package com.zry.xiaohongshu.count.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CountLikeUnlikeCommentMqDTO {
    private Long userId;
    private Long commentId;
    private Integer type;
}
