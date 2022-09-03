package com.sso.simple_sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bax
 * @create: 2022-08-07 00:43
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer Id;
    private String userName;
    private String passWord;
}
