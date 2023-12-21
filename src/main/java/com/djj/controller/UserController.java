package com.djj.controller;

import com.djj.entity.User;
import com.djj.service.UserService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String LIST = "user/list";
    private static final String ADD = "user/add";
    private static final String UPDATE = "user/update";

    @Autowired
    private UserService userService;

    //跳转添加页面
    @GetMapping("/add")
    public String create() {
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody User user) {
        int result = userService.create(user);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = userService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = userService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody User user) {
        int result = userService.update(user);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap) {
        User user = userService.detail(id);
        modelMap.addAttribute("user", user);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody User user) {
        List<User> list = userService.query(user);
        Integer count = userService.count(user);
        return MapControl.getInstance().success().page(list, count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

}
