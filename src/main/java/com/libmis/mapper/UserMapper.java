package com.libmis.mapper;

import com.libmis.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


/**
 * @author Ywqtttu
 * @date 2024-12-18
 */
public interface UserMapper extends BaseMapper<User>{
    @Select("Select * from user where user_name = #{userName}")
    User getByUserName(String username);

    @Insert("Insert into user (user_name, user_pwd) values (#{userName}, #{userPwd})")
    void register(String userName, String userPwd);
}
