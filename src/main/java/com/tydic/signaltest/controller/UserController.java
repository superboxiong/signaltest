package com.tydic.signaltest.controller;

import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.IUserRegister;
import com.tydic.signaltest.service.impl.UserRegisterImpl;
import com.tydic.signaltest.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
    private UserRegisterImpl userRegister;
    @Autowired
    ImgValidateCode imgValidateCode;
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
    @PostMapping("/userForgetPwd")
    @ApiOperation(value = "密码重置",notes = "用户根据验证码修改密码")
    public ResponseResult<String> userForgetPwd(SystemUser user) throws Exception {
        if(StringUtils.isBlank(user.getUserName()) || !CommonUtils.checkPhone(user.getUserName())){//手机号码不正确
            throw  new Exception("手机号码不正确");
        }
        if(StringUtils.isBlank(user.getPassword()) || !CommonUtils.checkPassword(user.getPassword())){
            throw new Exception("密码格式错误");
        }
        userRegister.userForgetPwd(user);
        return new ResponseResult<String>().getSuccess(null, MessageInfo.CHANGE_PASSWORD_FAILED);
    }
     @GetMapping("/sendImgCode")
     @ApiOperation(value = "图片验证码",notes = "用户使用手机号码获取图片验证码")
     public void sendImgCode(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,String> phoneNumber) throws Exception {
        if(StringUtils.isBlank(phoneNumber.get("phoneNumber")) || CommonUtils.checkPhone(phoneNumber.get("phoneNumber"))){
            throw new Exception("手机号码错误");
        }
         // 通知浏览器不要缓存
         response.setHeader("Expires", "-1");
         response.setHeader("Cache-Control", "no-cache");
         response.setHeader("Pragma", "-1");
         ImgValidateCode vCode = new ImgValidateCode(160,40,5,150);
         imgValidateCode.addCodeToRedis(vCode.getCode(),phoneNumber.get("phoneNumber"));
         // 输出到web页面
         ImageIO.write(vCode.getBuffImg(), "jpg", response.getOutputStream());
     }
     @PostMapping("/sendSms")
     @ApiOperation(value = "短信接口",notes = "用户注册短信验证码接口")
    public ResponseResult<String> sendSms(HttpServletRequest request, @RequestBody Map<String,String> phone) throws Exception {
        if(StringUtils.isBlank(phone.get("phone")) || !CommonUtils.checkPhone(phone.get("phone"))){
            throw new Exception("手机号码错误");
        }
        return new ResponseResult<String>().getSuccess(null,"验证码发送成功");
     }

     @PostMapping("/test")
     @ApiOperation(value = "test",notes = "test")
     public void test(@RequestBody Map<String,String> phone){
             userRegister.test(phone.get("phone"));
     }
}
