package com.tydic.signaltest.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tydic.signaltest.model.SystemUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author superxiong
 * @Date 2020/2/12 10:29
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<SystemUser> {
    @Insert("insert into system_user(id,user_name,password,register_time,head_img,status) values(3,#{user.userName},#{user.password},#{user.registerTime},'aaaa',#{user.status})")
    void insertIntoPg(@Param("user") SystemUser user);
    @Select("select * from system_user where user_name=#{phone}")
    SystemUser selectByPhone(@Param("phone") String phone);

}
