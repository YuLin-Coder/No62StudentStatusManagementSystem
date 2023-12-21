package com.djj.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//权限拦截器设计
public class PermissionInterceptor implements HandlerInterceptor {

    //预处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session对象
        HttpSession session = request.getSession();
        //判断用户是否登录
        if (session.getAttribute("user") != null) {
            //已经登录，放行
            return true;
        } else {
            //未登录，抛出异常
            throw new PermissionException("没有权限访问");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
