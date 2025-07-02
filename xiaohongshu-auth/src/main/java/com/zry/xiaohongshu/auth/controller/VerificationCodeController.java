package com.zry.xiaohongshu.auth.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.zry.xiaohongshu.auth.service.impl.VerificationCodeServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VerificationCodeController {
    @Resource
    private VerificationCodeServiceImpl verificationCodeService;
    @PostMapping("/verification/code/send")
    @ApiOperationLog(description = "发送验证码")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO){
        return verificationCodeService.send(sendVerificationCodeReqVO);
    }
}
