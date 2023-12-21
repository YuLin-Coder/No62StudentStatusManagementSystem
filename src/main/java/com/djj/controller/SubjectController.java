package com.djj.controller;

import com.djj.entity.Subject;
import com.djj.service.SubjectService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    private final String LIST = "subject/list";
    private final String ADD = "subject/add";
    private final String UPDATE = "subject/update";

    @Autowired
    private SubjectService subjectService;

    //跳转添加页面
    @GetMapping("/add")
    public String add() {
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(Subject subject) {
        int result = subjectService.create(subject);
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
        int result = subjectService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = subjectService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Subject subject) {
        int result = subjectService.update(subject);
        if (result <= 0) {
            //新增失败
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, ModelMap modelMap) {
        //查询单个专业信息，存储到request域，实现表单回显
        Subject subject = subjectService.detail(id);
        modelMap.addAttribute("subject", subject);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Subject subject) {
        List<Subject> list = subjectService.query(subject);
        Integer count = subjectService.count(subject);
        return MapControl.getInstance().success().put("data", list).put("count", count).getMap();
    }

    //跳转登录页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

}
