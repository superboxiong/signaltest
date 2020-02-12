package com.tydic.signaltest.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author superxiong
 * @Date 2020/2/11 14:34
 * @Version 1.0
 * 测试系统用户
 */
@Data
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 7981184993066601414L;
    /**
     * 用户id，及主键
     */
    private Long id;
    /**
     * 用户名（手机号码）
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 注册时间
     */
    private String registerTime;
    /***
     * 用户头像
     */
    private String headImg;
    /**
     * 用户状态
     */
    private boolean status;
    /**
     * 验证码
     */
    private String captcha;

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", headImg='" + headImg + '\'' +
                ", status=" + status +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
