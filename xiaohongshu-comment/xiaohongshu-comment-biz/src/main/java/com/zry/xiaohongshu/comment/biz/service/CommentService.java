package com.zry.xiaohongshu.comment.biz.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.comment.biz.model.vo.*;

public interface CommentService {
    Response<?> publishComment(PublishCommentReqVO publishCommentReqVO);
    PageResponse<FindCommentItemRspVO> findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);
    PageResponse<FindChildCommentItemRspVO> findChildCommentPageList(FindChildCommentPageListReqVO findChildCommentPageListReqVO);
    Response<?> likeComment(LikeCommentReqVO likeCommentReqVO);
}
