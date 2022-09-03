package com.sso.simple_sso.utils;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: bax
 * @create: 2022-08-07 13:50
 * @Description:
 */
@Data
public class Result {

        private Boolean flag;
        private Object data;
        private String msg;
        private Integer code;

        public Result(){

        }
        public Result(Boolean flag){
            this.flag = flag;
        }

        public Result(Boolean flag,Object data){
            this.flag = flag;
            this.data = data;
        }

        public Result(Boolean flag,String msg){
            this.flag = flag;
            this.msg = msg;
        }

        public Result(Boolean flag,String msg,Integer code){
            this.flag = flag;
            this.msg = msg;
            this.code = code;
        }

}
