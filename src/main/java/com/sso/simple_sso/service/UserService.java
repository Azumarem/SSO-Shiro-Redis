package com.sso.simple_sso.service;

import com.sso.simple_sso.pojo.Perms;
import com.sso.simple_sso.pojo.User;

import java.util.List;

public interface UserService {
    //注册用户方法
    void register(User user);

    //根据用户名查询业务的方法
    User findByUserName(String username);

    //根据用户名查询所有角色
    User findRolesByUserName(String username);

    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(String id);
}
