package com.sso.simple_sso.service.Impl;

import com.auth0.jwt.JWTVerifier;
import com.sso.simple_sso.mapper.UserMapper;
import com.sso.simple_sso.pojo.Perms;
import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import com.sso.simple_sso.utils.JwtUtil;
import com.sso.simple_sso.utils.SaltUtils;
import com.sun.jdi.ObjectCollectedException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: bax
 * @create: 2022-08-07 15:04
 * @Description:
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userDAO;


    @Override
    public List<Perms> findPermsByRoleId(String id) {
        return userDAO.findPermsByRoleId(id);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDAO.findRolesByUserName(username);
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findByUserName(username);
    }

    @Override
    public void register(User user) {
        //处理业务调用dao
        //1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2.将随机盐保存到数据
        user.setSalt(salt);
        //3.明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(md5Hash.toHex());
        userDAO.save(user);
    }
}
