package com.zry.xiaohongshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteDetailRspVO {

    private Long id;

    private Integer type;

    private String title;

    private String content;

    private List<String> imgUris;

    /**
     * 话题集合
     */
    List<FindTopicRspVO> topics;

    private Long creatorId;

    private String creatorName;

    private String avatar;

    private String videoUri;

    /**
     * 编辑时间
     */
    private String updateTime;

    /**
     * 是否可见
     */
    private Integer visible;

    private String likeTotal;
    private String collectTotal;
    private String commentTotal;
}