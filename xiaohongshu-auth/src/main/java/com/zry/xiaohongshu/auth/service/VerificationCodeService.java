package com.zry.xiaohongshu.auth.service;

import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

public interface VerificationCodeService {
    /**
    * @Description:  发送验证码
    * @Param: [sendVerificationCodeReqVO]
    * @return: com.zry.framework.common.reponse.Response<?>
    * @Author: zry
    * @Date: 2025/7/2
    */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
