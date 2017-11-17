package org.spring.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dell on 2017/11/17.
 */
@Controller
public class Login {
    //跳转到登录表单页面
    @RequestMapping(value="/user/user")
    public String loginUser() {
        return "/user/user";
    }
}
