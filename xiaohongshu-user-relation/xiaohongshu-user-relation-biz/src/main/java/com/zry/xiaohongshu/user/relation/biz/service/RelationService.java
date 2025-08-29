package com.zry.xiaohongshu.user.relation.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.relation.biz.model.vo.FollowUserReqVO;

public interface RelationService {
    Response<?> follow(FollowUserReqVO followUserReqVO);
}
