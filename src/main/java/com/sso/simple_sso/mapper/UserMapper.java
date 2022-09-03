package com.sso.simple_sso.mapper;

import com.sso.simple_sso.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("select * from user where username=#{username}")
    public User selectByUsername(String username);

    @Select("select * from user where password=#{password}")
    public User selectByPassword(String password);

    @Select("select * from user where username=#{username} && password=#{password}")
    public User selectByUserPWD(String username,String password);
}
