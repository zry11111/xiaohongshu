package com.zry.xiaohongshu.search.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserRspVO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 昵称高亮
    */
    private String highlightNickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 小红书ID
     */
    private String xiaohongshuId;

    /**
     * 笔记发布总数
     */
    private Integer noteTotal;

    /**
     * 粉丝总数
     */
    private String fansTotal;
}
