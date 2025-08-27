package com.zry.xiaohongshu.user.api;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.constant.ApiConstants;
import com.zry.xiaohongshu.user.dto.req.FindUserByIdReqDTO;
import com.zry.xiaohongshu.user.dto.req.FindUserByPhoneReqDTO;
import com.zry.xiaohongshu.user.dto.req.RegisterUserReqDTO;
import com.zry.xiaohongshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByIdRspDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {
    String PREFIX = "/user";
    @PostMapping(value = PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);
    @PostMapping(value = PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO);
    @PostMapping(value = PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO);
    @PostMapping(value = PREFIX + "/findById")
    Response<FindUserByIdRspDTO> findById(@RequestBody FindUserByIdReqDTO findUserByIdReqDTO);
}
