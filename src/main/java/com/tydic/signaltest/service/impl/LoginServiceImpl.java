package com.tydic.signaltest.service.impl;

import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.LoginService;
import com.tydic.signaltest.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired(required = false)
    private UserMapper userMapper;

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
}
