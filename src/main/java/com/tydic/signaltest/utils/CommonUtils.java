package com.tydic.signaltest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author superxiong
 * @Date 2020/2/12 9:43
 * @Version 1.0
 */
public class CommonUtils {
    public static final String PHONE_NUMBER="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";// 验证手机号
    public static final String PASSWORD="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static Pattern pattern=null;
    public static Matcher matcher=null;
    /**
     * 校验手机号码是否合法
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone){
           if(phone.length()!=11){
               return false;
           }else{
                pattern=Pattern.compile(PHONE_NUMBER);
                matcher=pattern.matcher(phone);
               return matcher.matches();
           }
    }

    /***
     * 密码校验（由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。）
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        if(password.length()>16 || password.length()<8){
            return false;
        }else{
            pattern=Pattern.compile(PASSWORD);
            matcher=pattern.matcher(password);
            return matcher.matches();
        }
    }
}
