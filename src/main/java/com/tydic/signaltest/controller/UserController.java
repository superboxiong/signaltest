package com.tydic.signaltest.controller;

import com.tydic.signaltest.mapper.UserMapper;
import com.tydic.signaltest.model.SystemUser;
import com.tydic.signaltest.service.IUserRegister;
import com.tydic.signaltest.service.LoginService;
import com.tydic.signaltest.utils.CommonUtils;
import com.tydic.signaltest.utils.ImgValidateCode;
import com.tydic.signaltest.utils.MessageInfo;
import com.tydic.signaltest.utils.ResponseResult;
import com.tydic.signaltest.service.impl.UserRegisterImpl;
import com.tydic.signaltest.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @Author superxiong
 * @Date 2020/2/11 16:49
 * @Version 1.0
 * 用户模块
 */


@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired(required = false)
    private IUserRegister userRegister;

    @Autowired
    private LoginService loginService;

    @Autowired
    ImgValidateCode imgValidateCode;
    @PostMapping(value = "/userRegister",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "用户注册",notes = "用户根据手机号码注册")
    public ResponseResult<String> userRegister(@RequestBody  SystemUser user) throws Exception {
       if(StringUtils.isBlank(user.getUserName()) || !CommonUtils.checkPhone(user.getUserName())){//手机号码不正确
           throw  new Exception("手机号码不正确");
       }
       if(StringUtils.isBlank(user.getPassword()) || !CommonUtils.checkPassword(user.getPassword())){
           throw new Exception("密码格式错误");
       }
       userRegister.userRegister(user);
       return new ResponseResult<String>().getSuccess(null, MessageInfo.REGISTER_SUCESSFUL);
    }



    /**
     * @author      jjq
     * @date        2020/2/14 16:15
     * 用户登录和记住密码
     */
    @GetMapping("/userLogin")
    @ApiOperation(value = "用户登录",notes = "用户用手机号登录")
    public ResponseResult<String> userLogin(String phone,String password,boolean flag,
                                            HttpServletResponse response){
        Map<String, Object> result = loginService.getUser(phone, password);
        if(Integer.parseInt(result.get("code").toString())==0){
            return new ResponseResult<String>().getFailure(MessageInfo.LOGIN_FAILED_NULL);
        }
        if (Integer.parseInt(result.get("code").toString())==1){

            if(flag==true){
                //判断用户是否记住密码
                    long createTime = System.currentTimeMillis()/1000;
                    String s = UUID.randomUUID().toString();
                    String md5ofAgent=MD5.getMD5ofStr(s);
                    String Phone_md5ofAgent = MD5.getMD5ofStr(phone+md5ofAgent);
                    SystemUser user = (SystemUser) result.get("user");
                    //将用户登录信息保存到数据库
                    loginService.setRememberMe(user,md5ofAgent,createTime);

                    Cookie cookie=new Cookie(phone,Phone_md5ofAgent);
                    cookie.setMaxAge(60*60*24*7);
                    cookie.setPath("/signaltest/user/userLogin");
                    response.addCookie(cookie);

                    //将phone和md5ofAgent返回到前端保存，记住密码登陆时取出来
                System.out.println("---login");
                    return new ResponseResult<String>().getSuccess(result.get("user").toString());

            }else{
                System.out.println("------login,不记住密码------");
                return new ResponseResult<String>().getSuccess(result.get("user").toString());
            }

        }else {
            return new ResponseResult<String>().getFailure(MessageInfo.LOGIN_FAILED);
        }


    }

    @PostMapping("/userForgetPwd")
    @ApiOperation(value = "密码重置",notes = "用户根据验证码修改密码")
    public ResponseResult<String> userForgetPwd(@RequestBody  SystemUser user) throws Exception {
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
     public void sendImgCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tempPhoneNumber=request.getParameter("phoneNumber"); //图片get请求 获取地址后面的参数
         String phoneNumber=tempPhoneNumber.substring(0,tempPhoneNumber.indexOf("?"));
        if(StringUtils.isBlank(phoneNumber) || !CommonUtils.checkPhone(phoneNumber)){
            throw new Exception("手机号码错误");
        }
//         ,@RequestBody Map<String,String> phoneNumber
         // 通知浏览器不要缓存
         response.setHeader("Expires", "-1");
         response.setHeader("Cache-Control", "no-cache");
         response.setHeader("Pragma", "-1");
         ImgValidateCode vCode = new ImgValidateCode(160,40,5,150);
         imgValidateCode.addCodeToRedis(vCode.getCode(),phoneNumber);
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
}
