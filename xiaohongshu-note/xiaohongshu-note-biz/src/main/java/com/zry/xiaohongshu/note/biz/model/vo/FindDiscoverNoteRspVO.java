package com.zry.xiaohongshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindDiscoverNoteRspVO {
    private String id;

    private Integer type;

    private String cover;

    private String videoUri;

    private String title;

    private Long creatorId;

    private String nickname;

    private String avatar;

    private String likeTotal;
}
