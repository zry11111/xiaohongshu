package com.zry.xiaohongshu.user.relation.biz.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.relation.biz.model.vo.FindFollowingListReqVO;
import com.zry.xiaohongshu.user.relation.biz.model.vo.FindFollowingUserRspVO;
import com.zry.xiaohongshu.user.relation.biz.model.vo.FollowUserReqVO;
import com.zry.xiaohongshu.user.relation.biz.model.vo.UnfollowUserReqVO;

public interface RelationService {
    Response<?> follow(FollowUserReqVO followUserReqVO);
    Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO);
    PageResponse<FindFollowingUserRspVO> findFollowingList(FindFollowingListReqVO findFollowingListReqVO);
}
