package com.tydic.signaltest.service.impl;

import com.tydic.signaltest.mapper.RememberMeMapper;
import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.RememberUser;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.LoginService;
import com.tydic.signaltest.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private RememberMeMapper rememberMeMapper;

    @Override
    public Map<String,Object> getUser(String phone, String password) {
        SystemUser systemUser = userMapper.selectUserByPhone(phone);
        Map map=new HashMap<String,Object>();
        System.out.println(systemUser);
        if(systemUser==null){
            map.put("code",0);
            return map;
        }
        String passwordByDB = systemUser.getPassword().trim();
        String md5ofPassword = MD5.getMD5ofStr(password);
        if(systemUser.getUserName().trim().equals(phone)&&passwordByDB.equals(md5ofPassword)){
            map.put("code",1);
            map.put("user",systemUser);
            return map;
        }else {
            map.put("code",-1);
            return map;
        }
    }


    @Override
    @Transactional
    public void setRememberMe(SystemUser user, String agent, long createTime) {
        System.out.println("----setRememberMe---");
        RememberUser rememberUser = new RememberUser();
        rememberUser.setRem_user_id(user.getId());
        rememberUser.setUser_agent(agent);
        rememberUser.setToken(MD5.getMD5ofStr(user.getUserName()+agent));
        rememberUser.setCreated(createTime);
        rememberUser.setExpires(60*60*24*7);
        //插入前先删除以前的记录
        rememberMeMapper.deleteRememberMe(user.getId());
        rememberMeMapper.insertRememberMe(rememberUser);
    }

    @Override
    public RememberUser getRememberMe(String token) {
        return rememberMeMapper.selectByTokenAndAgent(token);
    }

    @Override
    public SystemUser getUserByID(long id) {
        return userMapper.selectUserByUserId(id);
    }
}
