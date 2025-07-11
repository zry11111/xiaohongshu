package com.zry.xiaohongshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.zry.framework.biz.context.holder.LoginUserContextHolder;
import com.zry.framework.common.enums.DeletedEnum;
import com.zry.framework.common.enums.StatusEnum;
import com.zry.framework.common.exception.BizException;
import com.zry.framework.common.reponse.Response;
import com.zry.framework.common.util.JsonUtils;
import com.zry.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zry.xiaohongshu.auth.constant.RoleConstants;
import com.zry.xiaohongshu.auth.domain.dataobject.RoleDO;
import com.zry.xiaohongshu.auth.domain.dataobject.UserDO;
import com.zry.xiaohongshu.auth.domain.dataobject.UserRoleDO;
import com.zry.xiaohongshu.auth.domain.mapper.RoleDOMapper;
import com.zry.xiaohongshu.auth.domain.mapper.UserDOMapper;
import com.zry.xiaohongshu.auth.domain.mapper.UserRoleDOMapper;
import com.zry.xiaohongshu.auth.enums.LoginTypeEnum;
import com.zry.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zry.xiaohongshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.zry.xiaohongshu.auth.model.vo.user.UserLoginReqVO;
import com.zry.xiaohongshu.auth.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private UserRoleDOMapper userRoleDOMapper;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        // 获取信息
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();
        if(type!=0&&type!=1){
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
                UserDO userDO = userDOMapper.selectByPhone(phone);
                if(Objects.isNull(userDO)){
                    //TODO
                    userId = registerUser(phone);
                }else{
                    userId = userDO.getId();
                }
                break;
            case PASSWORD:
                //密码登录
                String password = userLoginReqVO.getPassword();
                // 根据手机号查询
                UserDO userDO1 = userDOMapper.selectByPhone(phone);

                // 判断该手机号是否注册
                if (Objects.isNull(userDO1)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }

                // 拿到密文密码
                String encodePassword = userDO1.getPassword();

                // 匹配密码是否一致
                boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);

                // 如果不正确，则抛出业务异常，提示用户名或者密码不正确
                if (!isPasswordCorrect) {
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }

                userId = userDO1.getId();
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

        // 获取当前请求对应的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        UserDO userDO = UserDO.builder()
                .id(userId)
                .password(encodePassword)
                .updateTime(LocalDateTime.now())
                .build();
        // 更新密码
        userDOMapper.updateByPrimaryKeySelective(userDO);

        return Response.success();
    }

    //这里改为使用编程式事务
    public Long registerUser(String phone){
        return transactionTemplate.execute(status -> {
            try{
                // 获取全局自增的ID
                Long xiaohongshuId = redisTemplate.opsForValue().increment(RedisKeyConstants.XIAOHONGSHU_ID_GENERATOR_KEY);

                UserDO userDO = UserDO.builder()
                        .phone(phone)
                        .xiaohongshuId(String.valueOf(xiaohongshuId)) // 自动生成小红书号 ID
                        .nickname("小红薯" + xiaohongshuId) // 自动生成昵称, 如：小红薯10000
                        .status(StatusEnum.ENABLED.getValue()) // 状态为启用
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.isValue()) // 逻辑删除
                        .build();

                // 添加入库
                userDOMapper.insert(userDO);

                // 获取刚刚添加入库的用户 ID
                Long userId = userDO.getId();

                // 给该用户分配一个默认角色
                UserRoleDO userRoleDO = UserRoleDO.builder()
                        .userId(userId)
                        .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.isValue())
                        .build();
                userRoleDOMapper.insert(userRoleDO);

                RoleDO roleDO = roleDOMapper.selectByPrimaryKey(RoleConstants.COMMON_USER_ROLE_ID);
                // 将该用户的角色 ID 存入 Redis 中
                List<String> roles = new ArrayList<>(1);
                roles.add(roleDO.getRoleKey());
                String userRolesKey = RedisKeyConstants.buildUserRoleKey(userId);
                redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

                return userId;
            }catch (Exception e){
                status.setRollbackOnly();//标记事务回滚
                return null;
            }
        });
    }
}
