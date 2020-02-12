package com.tydic.signaltest.utils;

/**
 * @Author superxiong
 * @Date 2020/2/11 17:39
 * @Version 1.0
 * 返回值类型常量类
 */
public class  MessageInfo {
    public static final String LOGIN_SUCESSFUL="登录成功";
    public static final String LOGIN_FAILED="账号或密码错误";
    public static final String REGISTER_SUCESSFUL="注册成功";
    public static final String REGISTER_FAILED="注册失败";
    public static final String CHANGE_PASSWORD_SUCESSFUL="修改成功";
    public static final String CHANGE_PASSWORD_FAILED="修改成功";
    public static final String PHONE_CAPCHA="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";// 验证手机号
}
