package com.zry.xiaohongshu.auth.rpc;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.api.UserFeignApi;
import com.zry.xiaohongshu.user.dto.req.FindUserByPhoneReqDTO;
import com.zry.xiaohongshu.user.dto.req.RegisterUserReqDTO;
import com.zry.xiaohongshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);

        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }
    public FindUserByPhoneRspDTO findUserByPhone(String phone){
        FindUserByPhoneReqDTO findUserByPhoneReqDTO = new FindUserByPhoneReqDTO();
        findUserByPhoneReqDTO.setPhone(phone);
        Response<FindUserByPhoneRspDTO> response = userFeignApi.findByPhone(findUserByPhoneReqDTO);
        if(!response.isSuccess()){
            return null;
        }
        return response.getData();
    }
    public void updatePassword(String encodePassword){
        UpdateUserPasswordReqDTO updateUserPasswordReqDTO = new UpdateUserPasswordReqDTO();
        updateUserPasswordReqDTO.setEncodedPassword(encodePassword);
        userFeignApi.updatePassword(updateUserPasswordReqDTO);
    }
}
