package com.tydic.signaltest.mapper;

import com.tydic.signaltest.model.RememberUser;
import org.apache.ibatis.annotations.*;

/**
 * @Author:         jjq
 * @CreateDate:     2020/2/15 11:10
 * @Version:        1.0
 * @Description:    记住密码
 */
@Mapper
public interface RememberMeMapper {

    /**
     * @author      jjq
     * @date        2020/2/14 16:15
     * 插入记住密码时登录的信息
     */
    @Insert("insert into remember_user(rem_user_id,user_agent,token,created,expires) values(#{re.rem_user_id},#{re.user_agent},#{re.token},#{re.created},#{re.expires})")
    void insertRememberMe(@Param("re") RememberUser rememberUser);

    /**
     * @author      jjq
     * @date        2020/2/14 16:15
     * 更新登录信息
     */
    @Delete("delete from remember_user where rem_user_id = #{user_id}")
    void deleteRememberMe(@Param("user_id") long user_id);


    @Select("select * from remember_user where token = #{token}")
    RememberUser selectByTokenAndAgent(@Param("token") String token);
}
