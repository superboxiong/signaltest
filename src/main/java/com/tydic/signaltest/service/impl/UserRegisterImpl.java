package com.tydic.signaltest.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.IUserRegister;
import com.tydic.signaltest.utils.ImgValidateCode;
import com.tydic.signaltest.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author superxiong
 * @Date 2020/2/12 9:33
 * @Version 1.0
 */
@Service
public class UserRegisterImpl extends ServiceImpl<UserMapper,SystemUser> implements IUserRegister  {
   @Autowired(required = false)
   private UserMapper userMapper;
   private static final String PREFIX="signal-captcha:sms:";//redis中验证码key前缀
   @Autowired
    StringRedisTemplate redisTemplate;
   @Autowired
    ImgValidateCode imgValidateCode;
    @Override
    public void userRegister(SystemUser user) throws Exception {
//       RedisUtils.checkCode(user.getCaptcha(),user.getUserName());
//        checkCode(user.getCaptcha(),user.getUserName());
        imgValidateCode.checkCode(user.getCaptcha(),user.getUserName());
        Wrapper<SystemUser> wrapper=new EntityWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        if(selectOne(wrapper)!=null){
            throw new Exception("手机号已经存在");
        }
        //使用MD5将密码加密
        user.setPassword(MD5.getMD5ofStr(user.getPassword()));
        user.setRegisterTime(new Date());
        user.setStatus(true);
        insert(user);

    }

    @Override
    public void userForgetPwd(SystemUser user) throws Exception {
        Wrapper<SystemUser> wrapper=new EntityWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        if(selectOne(wrapper)==null){
            throw new Exception("账号不存在");
        }
//        new ImgValidateCode().checkCode(user.getCaptcha(),user.getUserName());
        user.setPassword(MD5.getMD5ofStr(user.getPassword()));
        update(user,wrapper);
    }




}
