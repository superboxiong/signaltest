package com.tydic.signaltest.controller;

import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.IUserRegister;
import com.tydic.signaltest.utils.CommonUtils;
import com.tydic.signaltest.utils.MessageInfo;
import com.tydic.signaltest.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author superxiong
 * @Date 2020/2/11 16:49
 * @Version 1.0
 * 用户模块
 */
@RestController
@Api(tags = "用户模块")
public class UserController {
    @Autowired(required = false)
    private IUserRegister userRegister;
    @PostMapping("/userRegister")
    @ApiOperation(value = "用户注册",notes = "用户根据手机号码注册")
    public ResponseResult<String> userRegister(SystemUser user) throws Exception {
       if(StringUtils.isBlank(user.getUserName()) || !CommonUtils.checkPhone(user.getUserName())){//手机号码不正确
           throw  new Exception("手机号码不正确");
       }
       if(StringUtils.isBlank(user.getPassword()) || !CommonUtils.checkPassword(user.getPassword())){
           throw new Exception("密码格式错误");
       }
       userRegister.userRegister(user);
       return new ResponseResult<String>().getSuccess(null, MessageInfo.REGISTER_SUCESSFUL);
    }

}
