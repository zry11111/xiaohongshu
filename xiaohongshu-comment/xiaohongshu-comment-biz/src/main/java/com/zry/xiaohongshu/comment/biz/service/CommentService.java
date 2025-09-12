package com.zry.xiaohongshu.comment.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.comment.biz.model.vo.PublishCommentReqVO;

public interface CommentService {
    Response<?> publishComment(PublishCommentReqVO publishCommentReqVO);
}
