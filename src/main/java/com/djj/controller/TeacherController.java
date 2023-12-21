package com.djj.controller;

import com.djj.entity.Teacher;
import com.djj.service.TeacherService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final String LIST = "teacher/list";
    private final String ADD = "teacher/add";
    private final String UPDATE = "teacher/update";

    @Autowired
    private TeacherService teacherService;

    //跳转添加页面
    @GetMapping("/add")
    public String add() {
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(Teacher teacher) {
        int result = teacherService.create(teacher);
        if (result <= 0) {
            //新增失败
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = teacherService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = teacherService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Teacher teacher) {
        int result = teacherService.update(teacher);
        if (result <= 0) {
            //新增失败
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, ModelMap modelMap) {
        //查询要修改的老师
        Teacher teacher = teacherService.detail(id);
        modelMap.addAttribute("teacher", teacher);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Teacher teacher) {
        List<Teacher> list = teacherService.query(teacher);
        Integer count = teacherService.count(teacher);
        return MapControl.getInstance().success().put("data", list).put("count", count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }
}
