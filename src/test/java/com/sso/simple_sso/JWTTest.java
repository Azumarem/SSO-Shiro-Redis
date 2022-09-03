package com.sso.simple_sso;

import com.sso.simple_sso.mapper.UserMapper;
import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: bax
 * @create: 2022-08-07 00:31
 * @Description:
 */
@Slf4j
@SpringBootTest
public class JWTTest {

    @Autowired
    UserMapper userMapper;
    //public String JWT(){
    //    JwtUtil jwtUtil = new JwtUtil();
    //
    //}
    @Test
    public void testSelect(){
        String s = userMapper.selectByUserPWD("bax123","123456").toString();
        System.out.println(s);

    }
}
