package com.zry.xiaohongshu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zry.framework.common.exception.BizException;
import com.zry.framework.common.reponse.Response;
import com.zry.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zry.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zry.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.zry.xiaohongshu.auth.service.VerificationCodeService;
import com.zry.xiaohongshu.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private AliyunSmsHelper aliyunSmsHelper;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        String phone = sendVerificationCodeReqVO.getPhone();
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);
        Boolean isSent = redisTemplate.hasKey(key);
        if(isSent){
            //验证码发送频繁
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        //生成随机六位数验证码
        String code = RandomUtil.randomNumbers(6);
        log.info("==> 手机号: {}, 已生成验证码：【{}】", phone, code);

        // 调用第三方短信发送服务
        threadPoolTaskExecutor.submit(() -> {
            String signName = "阿里云短信测试";
            String templateCode = "SMS_154950909";
            String templateParam = String.format("{\"code\":\"%s\"}", code);
            aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
        });
        //将验证码存入Redis，设置过期时间为3分钟
        redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
        return Response.success();
    }
}
