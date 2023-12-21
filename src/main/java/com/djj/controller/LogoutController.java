package com.djj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    //退出操作
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //让session失效
        session.invalidate();
        //重定向到登录页
        return "redirect:/";
    }

}
