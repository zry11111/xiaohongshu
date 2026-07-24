package com.zry.xiaohongshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteItemRspVO {

    private String noteId;

//    private Integer type;

    private String cover;

    private String videoUri;

    private String title;

//    private Long creatorId;

//    private String nickname;

//    private String avatar;

    private String likeTotal;
    private String collectTotal;
    private String commentTotal;

//    private Boolean isLiked;
    private LocalDateTime createTime;
    private Integer status;
    private Boolean isTop;
    private Integer visible;

}
