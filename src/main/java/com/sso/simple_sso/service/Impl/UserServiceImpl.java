package com.sso.simple_sso.service.Impl;

import com.auth0.jwt.JWTVerifier;
import com.sso.simple_sso.mapper.UserMapper;
import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import com.sso.simple_sso.utils.JwtUtil;
import com.sun.jdi.ObjectCollectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: bax
 * @create: 2022-08-07 15:04
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User Login(String username,String password) {

        return userMapper.selectByUserPWD(username,password);
    }
}
