package com.tydic.signaltest.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * @Author:         jjq
 * @CreateDate:     2020/2/14 15:41
 * @Version:        1.0
 * @Description:    记住密码
 */
@Data
@TableName("remember_user")
public class RememberUser {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private long  rem_user_id;
    /**
     *用户代理，用于判断是否为合法登录（MD5加密）
     */
    private String user_agent;
    /**
     * 登录令牌（loginName+user_agent）
     */
    private String token;

//    private String type;
    /**
     * 创建时间（从1970年开始到当前时刻地秒数）
     */
    private long created;
    /**
     * 存活时间
     */
    private long expires;
}
