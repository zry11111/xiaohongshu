package com.zry.xiaohongshu.count.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdRspDTO;

public interface UserCountService {
    Response<FindUserCountsByIdRspDTO> findUserCountData(FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);
}
