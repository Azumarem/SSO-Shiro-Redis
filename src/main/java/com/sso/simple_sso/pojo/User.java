package com.sso.simple_sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author: bax
 * @create: 2022-08-07 00:43
 * @Description:
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class User implements Serializable {
    private String  id;
    private String username;
    private String password;
    private String salt;

    //定义角色集合
    private List<Role> roles;
}
