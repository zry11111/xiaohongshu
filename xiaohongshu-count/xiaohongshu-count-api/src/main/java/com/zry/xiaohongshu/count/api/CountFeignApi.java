package com.zry.xiaohongshu.count.api;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.count.constant.ApiConstants;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdReqDTO;
import com.zry.xiaohongshu.count.dto.FindUserCountsByIdRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface CountFeignApi {

    String PREFIX = "/count";
    @PostMapping(value = PREFIX + "/user/data")
    Response<FindUserCountsByIdRspDTO> findUserCount(@RequestBody FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);

}