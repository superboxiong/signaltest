package com.tydic.signaltest.service;

import com.baomidou.mybatisplus.service.IService;
import com.tydic.signaltest.model.SystemUser;

/**
 * @Author superxiong
 * @Date 2020/2/12 9:32
 * @Version 1.0
 */
public interface IUserRegister extends IService<SystemUser> {
    /***
     * 用户注册
     * @param user
     */
    void userRegister(SystemUser user) throws Exception;

    /**
     * 用户重置密码
     * @param user
     */
    void userForgetPwd(SystemUser user) throws Exception;


}
