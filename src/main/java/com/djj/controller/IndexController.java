package com.djj.controller;

import com.djj.entity.*;
import com.djj.service.*;
import com.djj.utils.MD5Utils;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    SectionService sectionService;
    @Autowired
    ScoreService scoreService;

    //跳转系统主页
    @GetMapping("/index")
    public String login() {
        return "index";
    }

    //跳转用户基本信息页面
    @GetMapping("/info")
    public String info() {
        return "info";
    }

    //跳转修改密码页面
    @GetMapping("/pwd")
    public String pwd() {
        return "pwd";
    }

    //修改密码 根据旧密码来修改密码
    @PostMapping("/pwd")
    @ResponseBody
    public Map<String, Object> pwd(String sourcePwd, String newPwd, String type, Integer id) {
        //先判断类型
        if ("1".equals(type)) {
            User user = userService.detail(id);
            //比较原密码是否相同 注意：原密码也要加密后再进行比较，因为数据库中存储的是加密后的密码
            if (user.getUserPwd().equals(MD5Utils.getMD5(sourcePwd))) {
                User entity = new User();
                entity.setId(id);
                entity.setUserPwd(MD5Utils.getMD5(newPwd)); //主要要加密
                int result = userService.update(entity);
                if (result <= 0) {
                    return MapControl.getInstance().error().getMap();
                } else {
                    return MapControl.getInstance().success().getMap();
                }
            } else {
                return MapControl.getInstance().error("原密码错误").getMap();
            }
        }
        if ("2".equals(type)) {
            Teacher teacher = teacherService.detail(id);
            //比较原密码
            if (teacher.getTeacherPwd().equals(MD5Utils.getMD5(sourcePwd))) {
                Teacher entity = new Teacher();
                entity.setId(id);
                entity.setTeacherPwd(MD5Utils.getMD5(newPwd));
                int result = teacherService.update(entity);
                if (result <= 0) {
                    return MapControl.getInstance().error().getMap();
                } else {
                    return MapControl.getInstance().success().getMap();
                }
            } else {
                return MapControl.getInstance().error("原密码错误").getMap();
            }
        }
        if ("3".equals(type)) {
            Student student = studentService.detail(id);
            //比较原密码
            if (student.getStuPwd().equals(MD5Utils.getMD5(sourcePwd))) {
                Student entity = new Student();
                entity.setId(id);
                entity.setStuPwd(MD5Utils.getMD5(newPwd));
                int result = studentService.update(entity);
                if (result <= 0) {
                    return MapControl.getInstance().error().getMap();
                } else {
                    return MapControl.getInstance().success().getMap();
                }
            } else {
                return MapControl.getInstance().error("原密码错误").getMap();
            }
        }

        return MapControl.getInstance().error().getMap();
    }

    //跳转系统主页（数据概览）
    @GetMapping("/main")
    public String main(ModelMap modelMap) {
        //1.系统数据概览
        List<Clazz> clazzes = clazzService.query(null);
        List<Subject> subjects = subjectService.query(null);
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        List<Section> sections = sectionService.query(null);
        List<Student> students = studentService.query(null);
        modelMap.addAttribute("clazzCnt", clazzes.size());
        modelMap.addAttribute("subjectCnt", subjects.size());
        modelMap.addAttribute("teacherCnt", teachers.size());
        modelMap.addAttribute("courseCnt", courses.size());
        modelMap.addAttribute("studentCnt", students.size());
        modelMap.addAttribute("sectionCnt", sections.size());

        //2.班级学生数量
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Clazz clazz : clazzes) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", clazz.getClazzName()); //设置班级名称
            int cnt = 0;
            //统计学生数量
            for (Student student : students) {
                if (student.getClazzId() == clazz.getId()) {
                    cnt++;
                }
            }
            map.put("cnt", cnt); //设置学生数量
            mapList.add(map);
        }
        modelMap.addAttribute("mapList", mapList);

        //3.查询各科平均成绩（根据专业查询各科平均成绩）
        List<HashMap> mapList2 = scoreService.queryAvgScoreBySection();
        modelMap.addAttribute("mapList2", mapList2);

        return "main";
    }

}
