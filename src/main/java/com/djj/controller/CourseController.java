package com.djj.controller;

import com.djj.entity.Course;
import com.djj.service.CourseService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController {

    private static final String LIST = "course/list";
    private static final String ADD = "course/add";
    private static final String UPDATE = "course/update";

    @Autowired
    private CourseService courseService;

    //跳转添加页面
    @GetMapping("/add")
    public String create() {
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Course course) {
        int result = courseService.create(course);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = courseService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = courseService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Course course) {
        int result = courseService.update(course);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap) {
        //查询出要修改的课程信息，存储到request域
        Course course = courseService.detail(id);
        modelMap.addAttribute("course", course);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Course course) {
        List<Course> list = courseService.query(course);
        //查询总记录条数
        Integer count = courseService.count(course);
        return MapControl.getInstance().success().page(list, count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

}
