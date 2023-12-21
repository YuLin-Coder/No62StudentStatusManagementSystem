package com.djj.controller;

import com.djj.entity.Clazz;
import com.djj.entity.Subject;
import com.djj.service.ClazzService;
import com.djj.service.SubjectService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clazz")
public class ClazzController {

    private static final String LIST = "clazz/list";
    private static final String ADD = "clazz/add";
    private static final String UPDATE = "clazz/update";

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SubjectService subjectService;

    //跳转添加页面
    @GetMapping("/add")
    public String create(ModelMap modelMap) {
        //查询所有的专业，存储到request域
        List<Subject> subjects = subjectService.query(null);
        modelMap.addAttribute("subjects", subjects);
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Clazz clazz) {
        int result = clazzService.create(clazz);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = clazzService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = clazzService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Clazz clazz) {
        int result = clazzService.update(clazz);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap) {
        //查询所有的专业
        List<Subject> subjects = subjectService.query(null);
        //查询出要修改的班级的信息
        Clazz clazz = clazzService.detail(id);
        //将查询出来的数据存储到request域，实现表单回显
        modelMap.addAttribute("clazz", clazz);
        modelMap.addAttribute("subjects", subjects);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Clazz clazz) {
        //查询所有的班级
        List<Clazz> list = clazzService.query(clazz);
        //查询所有的专业
        List<Subject> subjects = subjectService.query(null);
        //设置关联
        list.forEach(entity -> {
            subjects.forEach(subject -> {
                //判断班级表中subjectId与专业表的id是否一致
                if (entity.getSubjectId() == subject.getId()) {
                    entity.setSubject(subject);
                }
            });
        });
        //查询班级总数
        Integer count = clazzService.count(clazz);
        return MapControl.getInstance().success().page(list, count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

}
