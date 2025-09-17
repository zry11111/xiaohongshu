package com.zry.xiaohongshu.user.biz.controller;

import com.zry.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.user.biz.model.vo.FindUserProfileReqVO;
import com.zry.xiaohongshu.user.biz.model.vo.FindUserProfileRspVO;
import com.zry.xiaohongshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.zry.xiaohongshu.user.biz.service.UserService;
import com.zry.xiaohongshu.user.biz.service.impl.UserServiceImpl;
import com.zry.xiaohongshu.user.dto.req.FindUserByIdReqDTO;
import com.zry.xiaohongshu.user.dto.req.FindUsersByIdsReqDTO;
import com.zry.xiaohongshu.user.dto.req.RegisterUserReqDTO;
import com.zry.xiaohongshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByIdRspDTO;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateUserInfo(@Validated UpdateUserInfoReqVO updateUserInfoReqVO) {
        return userService.updateUserInfo(updateUserInfoReqVO);
    }
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }
    @PostMapping("/findByPhone")
    @ApiOperationLog(description = "根据手机号查询用户信息")
    public Response<FindUserByPhoneRspDTO> findByPhone(@Validated @RequestBody com.zry.xiaohongshu.user.dto.req.FindUserByPhoneReqDTO findUserByPhoneReqDTO) {
        return userService.findByPhone(findUserByPhoneReqDTO);
    }
    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        return userService.updatePassword(updateUserPasswordReqDTO);
    }
    @PostMapping("/findById")
    @ApiOperationLog(description = "根据用户 ID 查询用户信息")
    public Response<FindUserByIdRspDTO> findById(@Validated @RequestBody FindUserByIdReqDTO findUserByIdReqDTO) {
        return userService.findById(findUserByIdReqDTO);
    }
    @PostMapping("/findByIds")
    @ApiOperationLog(description = "批量查询用户信息")
    public Response<List<FindUserByIdRspDTO>> findByIds(@Validated @RequestBody FindUsersByIdsReqDTO findUsersByIdsReqDTO) {
        return userService.findByIds(findUsersByIdsReqDTO);
    }
    @PostMapping(value = "/profile")
    public Response<FindUserProfileRspVO> findUserProfile(@Validated @RequestBody FindUserProfileReqVO findUserProfileReqVO) {
        return userService.findUserProfile(findUserProfileReqVO);
    }

}
