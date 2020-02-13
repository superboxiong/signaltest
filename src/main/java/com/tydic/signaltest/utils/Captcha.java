package com.tydic.signaltest.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author superxiong
 * @Date 2020/2/12 14:16
 * @Version 1.0
 * 验证码工具类
 */
public class Captcha {

    private static final String PREFIX="signal-captcha:sms:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 短信验证码获取
     * @param request
     * @param phoneNumber
     * @return
     */
    public boolean getSmsCaptcha(HttpServletRequest request,String phoneNumber){
        //判断redis是否存在验证码
       String captchaCode=redisTemplate.opsForValue().get(PREFIX+phoneNumber);
       if(StringUtils.isNotBlank(captchaCode)){//存在验证码 将其删除
           redisTemplate.delete(PREFIX+phoneNumber);
       }
       //随机生成6位数的验证码
        captchaCode= RandomStringUtils.randomNumeric(6);
       //验证码发送
        if(sendSmsCode(phoneNumber,captchaCode)){//验证码发送成功，保存到redis中，且有效时间为5分钟
           redisTemplate.opsForValue().set(PREFIX+phoneNumber,captchaCode,5*60, TimeUnit.SECONDS);
           return true;
        }else{
            return false;
        }

    }

    public void checkSmsCode(String phoneNumber,String capchaCode) throws Exception {
        String tempCode=PREFIX+phoneNumber;//拼接临时字符串和redis进行匹配
        //从redis中获取缓存验证码
        String redisSmsCode=redisTemplate.opsForValue().get(tempCode);
        if(StringUtils.isBlank(redisSmsCode)){//redis中不存在验证码
            throw new Exception("验证码已经失效");
        }
        if(!capchaCode.equals(redisSmsCode)){//验证码错误
            throw new Exception("验证码错误");
        }
        //验证一次删除redis的缓存验证码
        redisTemplate.delete(tempCode);

    }
    public boolean sendSmsCode(String phoneNumber,String captchaCode){
        return true;
    }
}
