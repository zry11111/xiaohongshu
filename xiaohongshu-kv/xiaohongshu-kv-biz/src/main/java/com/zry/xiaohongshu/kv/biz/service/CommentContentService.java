package com.zry.xiaohongshu.kv.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.dto.req.BatchAddCommentContentReqDTO;

public interface CommentContentService {
    /**
     * 批量添加评论内容
     * @param batchAddCommentContentReqDTO
     * @return
     */
    Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO);
}
