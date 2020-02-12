package com.tydic.signaltest.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.IUserRegister;
import com.tydic.signaltest.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public void userRegister(SystemUser user) {
         //使用MD5将密码加密
        user.setId(4L);
        user.setPassword(MD5.getMD5ofStr(user.getPassword()));
        user.setRegisterTime(new Date());
        user.setStatus(true);
        user.setHeadImg("qqqq");
//        userMapper.insertIntoPg(user);
        insert(user);
//        Wrapper<SystemUser> wrapper=new EntityWrapper<>();

    }
}
