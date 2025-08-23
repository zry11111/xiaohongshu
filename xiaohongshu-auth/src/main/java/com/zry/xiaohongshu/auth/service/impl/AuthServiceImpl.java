package com.zry.xiaohongshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.zry.framework.biz.context.holder.LoginUserContextHolder;
import com.zry.framework.common.exception.BizException;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zry.xiaohongshu.auth.enums.LoginTypeEnum;
import com.zry.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zry.xiaohongshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.zry.xiaohongshu.auth.model.vo.user.UserLoginReqVO;
import com.zry.xiaohongshu.auth.rpc.UserRpcService;
import com.zry.xiaohongshu.auth.service.AuthService;
import com.zry.xiaohongshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;
    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        // 获取信息
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();
        if(type!=2&&type!=1){
            return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(),"登录方式不正确");
        }
        Long userId = null;
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);

        switch (loginTypeEnum){
            case VERIFICATION_CODE:
                //验证码登录
                String code = userLoginReqVO.getCode();
                Preconditions.checkArgument(StringUtils.isNotBlank(code), "验证码不能为空");
                //生成对应的key获取验证码
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                String sentCode = (String) redisTemplate.opsForValue().get(key);
                if(!StringUtils.equals(code,sentCode)){
                    //验证码错误
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }
                // 验证码登录逻辑
                //先判断该手机号是否已经注册
                Long userIdTmp = userRpcService.registerUser(phone);
                if(Objects.isNull(userIdTmp)){
                    //如果没有注册成功，则抛出业务异常
                    throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                }
                userId = userIdTmp;
                break;
            case PASSWORD:
                //密码登录
                String password = userLoginReqVO.getPassword();
                // 根据手机号查询
                FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);

                // 判断该手机号是否注册
                if (Objects.isNull(findUserByPhoneRspDTO)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }

                // 拿到密文密码
                String encodePassword = findUserByPhoneRspDTO.getPassword();


                // 匹配密码是否一致
                boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);

                // 如果不正确，则抛出业务异常，提示用户名或者密码不正确
                if (!isPasswordCorrect) {
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }

                userId = findUserByPhoneRspDTO.getId();
                break;
            default:
                break;

        }
        // 生成token
        StpUtil.login(userId);
        // 获取令牌
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return Response.success(tokenInfo.tokenValue);
    }

    @Override
    public Response<?> logout() {
        Long userId = LoginUserContextHolder.getUserId();
        StpUtil.logout(userId);
        return Response.success();
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO) {
        // 新密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword = passwordEncoder.encode(newPassword);

        userRpcService.updatePassword(encodePassword);

        return Response.success();
    }
}
