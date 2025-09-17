package com.zry.xiaohongshu.kv.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.zry.xiaohongshu.kv.dto.req.DeleteCommentContentReqDTO;

public interface CommentContentService {
    /**
     * 批量添加评论内容
     * @param batchAddCommentContentReqDTO
     * @return
     */
    Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO);
    /**
     * 批量查询评论内容
     * @param batchFindCommentContentReqDTO
     * @return
     */
    Response<?> batchFindCommentContent(BatchFindCommentContentReqDTO batchFindCommentContentReqDTO);
    Response<?> deleteCommentContent(DeleteCommentContentReqDTO deleteCommentContentReqDTO);
}
