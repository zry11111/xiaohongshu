package com.zry.xiaohongshu.search.biz.service;


import com.zry.framework.common.reponse.PageResponse;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserReqVO;
import com.zry.xiaohongshu.search.biz.model.vo.SearchUserRspVO;
import com.zry.xiaohongshu.search.dto.RebuildUserDocumentReqDTO;

public interface UserService {
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);
    /**
     * 重建用户文档
     * @param rebuildUserDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO);
}
