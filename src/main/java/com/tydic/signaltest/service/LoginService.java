package com.tydic.signaltest.service;


import com.tydic.signaltest.model.RememberUser;
import com.tydic.signaltest.model.SystemUser;

import java.util.Map;
/**
 * @Author:         jjq
 * @CreateDate:     2020/2/14 16:48
 * @Version:        1.0
 * @Description:    TDD
 */
public interface LoginService {

    Map<String,Object> getUser(String phone, String password) ;
    void setRememberMe(SystemUser user,String agent,long createTime);

    RememberUser getRememberMe(String token);

    SystemUser getUserByID(long id);

}
