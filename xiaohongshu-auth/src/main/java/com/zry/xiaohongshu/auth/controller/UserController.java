package com.zry.xiaohongshu.auth.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.zry.xiaohongshu.auth.model.vo.user.UserLoginReqVO;
import com.zry.xiaohongshu.auth.service.UserService;
import com.zry.xiaohongshu.auth.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO userLoginReqVO) {
        return userService.loginAndRegister(userLoginReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "用户登出")
    public Response<?> logout() {
        log.info("用户登出");
        return userService.logout();
    }
    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO updatePasswordReqVO) {
        return userService.updatePassword(updatePasswordReqVO);
    }
}
