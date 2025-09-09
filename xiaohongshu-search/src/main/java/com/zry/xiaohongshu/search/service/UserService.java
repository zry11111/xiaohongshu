package com.zry.xiaohongshu.search.service;

import com.zry.framework.common.reponse.PageResponse;
import com.zry.xiaohongshu.search.model.vo.SearchUserReqVO;
import com.zry.xiaohongshu.search.model.vo.SearchUserRspVO;

public interface UserService {
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);
}
