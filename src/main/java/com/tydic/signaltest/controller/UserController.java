package com.tydic.signaltest.controller;

import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.utils.MessageInfo;
import com.tydic.signaltest.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/userRegister")
    @ApiOperation(value = "用户注册",notes = "用户根据手机号码注册")
    public ResponseResult<String> userRegister(SystemUser user){
        System.out.println(user.toString());
       return new ResponseResult<String>().getSuccess(null, MessageInfo.REGISTER_SUCESSFUL);
    }
}
