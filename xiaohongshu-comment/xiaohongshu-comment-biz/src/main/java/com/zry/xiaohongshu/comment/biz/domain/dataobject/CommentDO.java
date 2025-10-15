package com.zry.xiaohongshu.comment.biz.domain.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDO {
    private Long id;

    private Long noteId;

    private Long userId;

    private String contentUuid;

    private Boolean isContentEmpty;

    private String imageUrl;

    private Integer level;

    private Long replyTotal;

    private Long likeTotal;

    private Long parentId;

    private Long replyCommentId;

    private Long replyUserId;

    private Integer isTop;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    private Long childCommentTotal;
    private Long firstReplyCommentId;
    /**
     * 热度值
     */
    private Double heat;
}