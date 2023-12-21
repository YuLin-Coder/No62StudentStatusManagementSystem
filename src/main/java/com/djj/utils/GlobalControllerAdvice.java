package com.djj.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//全局异常处理类
@ControllerAdvice
public class GlobalControllerAdvice {

    private final String ERROR = "error";

    @ExceptionHandler(value = PermissionException.class)
    public ModelAndView noPermission(PermissionException e) {
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject(ERROR, e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Map<String, Object> runtimeException(RuntimeException e) {
        e.printStackTrace();
        return MapControl.getInstance().error().getMap();
    }


}
