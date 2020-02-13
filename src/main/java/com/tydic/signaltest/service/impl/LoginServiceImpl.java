package com.tydic.signaltest.service.impl;

import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.LoginService;
import com.tydic.signaltest.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public int getUser(String phone, String password) {
        SystemUser systemUser = userMapper.selectUserByPhone(phone);
        if(systemUser==null){
            return 0;
        }
        String passwordByDB = systemUser.getPassword();
        String md5ofPassword = MD5.getMD5ofStr(password);
        if(systemUser.getUserName().equals(phone)&&passwordByDB.equals(md5ofPassword)){
            return 1;
        }else {
            return -1;
        }
    }
}
