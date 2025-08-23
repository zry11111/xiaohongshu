package com.zry.xiaohongshu.user.biz.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.zry.xiaohongshu.user.dto.req.FindUserByPhoneReqDTO;
import com.zry.xiaohongshu.user.dto.req.RegisterUserReqDTO;
import com.zry.xiaohongshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;

public interface UserService {
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);
}
