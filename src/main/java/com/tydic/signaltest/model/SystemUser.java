package com.tydic.signaltest.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author superxiong
 * @Date 2020/2/11 14:34
 * @Version 1.0
 * 测试系统用户
 */
@Data
@TableName("system_user")
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 7981184993066601414L;
    /**
     * 用户id，及主键
     */
    @TableId(type =IdType.AUTO)
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
    private Date registerTime;
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
    @TableField(exist = false)
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
