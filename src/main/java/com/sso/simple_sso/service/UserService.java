package com.sso.simple_sso.service;

import com.sso.simple_sso.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User Login(String username,String password);
}
