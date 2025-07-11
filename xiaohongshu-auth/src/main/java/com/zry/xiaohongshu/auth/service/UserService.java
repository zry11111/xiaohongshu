package com.zry.xiaohongshu.auth.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.zry.xiaohongshu.auth.model.vo.user.UserLoginReqVO;

public interface UserService {
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);
    Response<?> logout();
    Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO);
}
