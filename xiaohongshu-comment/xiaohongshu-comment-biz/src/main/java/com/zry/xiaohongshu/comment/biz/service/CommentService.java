package com.zry.xiaohongshu.comment.biz.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.comment.biz.model.vo.FindCommentItemRspVO;
import com.zry.xiaohongshu.comment.biz.model.vo.FindCommentPageListReqVO;
import com.zry.xiaohongshu.comment.biz.model.vo.PublishCommentReqVO;

public interface CommentService {
    Response<?> publishComment(PublishCommentReqVO publishCommentReqVO);
    PageResponse<FindCommentItemRspVO> findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);
}
