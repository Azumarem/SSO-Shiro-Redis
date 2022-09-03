package com.sso.simple_sso.utils;

import io.jsonwebtoken.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.sso.simple_sso.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: bax
 * @create: 2022-08-06 23:41
 * @Description:
 */
@Slf4j
public class JwtUtil {

        public static String geToken(User user, long TTLMillis) {
            //Jwts.builder()生成
            //Jwts.parser()验证
            long nowMillis = System.currentTimeMillis();
            JwtBuilder jwtBuilder =  Jwts.builder()
                    .setId(user.getId()+"")
                    .setSubject(user.getUserName())    //用户名
                    .setIssuedAt(new Date())//登录时间
                    .signWith(SignatureAlgorithm.HS256, "my-123");
            if (TTLMillis >= 0){
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                log.info("失效时间为"+ exp);
                jwtBuilder.setExpiration(exp);
            }
            //设置过期时间
            //前三个为载荷playload 最后一个为头部 header
            System.out.println(jwtBuilder.compact());
            return  jwtBuilder.compact();
    }


    //token的解析
    //有状态登录  服务器端保存用户信息
    //无状态登录  服务器端没有保存用户信息   无状态效率比有状态效率高
        public static Claims tokenToOut(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey("my-123")
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("用户id:"+claims.getId());
            System.out.println("用户名:"+claims.getSubject());
            System.out.println("用户时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    format(claims.getIssuedAt()));System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    format(claims.getExpiration()));
            System.out.println("用户角色:"+claims.get("role"));
            return claims;

        }


}
