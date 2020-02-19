package com.tydic.signaltest.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tydic.signaltest.model.RememberUser;
import com.tydic.signaltest.model.SystemUser;
import org.apache.ibatis.annotations.*;
import sun.util.resources.ga.LocaleNames_ga;

/**
 * @Author superxiong
 * @Date 2020/2/12 10:29
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<SystemUser> {
    @Insert("insert into system_user(id,user_name,password,register_time,head_img,status) values(3,#{user.userName},#{user.password},#{user.registerTime},'aaaa',#{user.status})")
    void insertIntoPg(@Param("user") SystemUser user);


    /**
     * @author      jjq
     * @date        2020/2/14 16:15
     * 登陆验证
     */
    @Select("select * from system_user where user_name = #{phone}")
    SystemUser selectUserByPhone(@Param("phone") String phone);

    /**
     * @author      jjq
     * @date        2020/2/14 16:15
     * 根据用户id查找用户
     */
    @Select("select * from system_user where id = #{user_id}")
    SystemUser selectUserByUserId(@Param("user_id") long user_id);

}
