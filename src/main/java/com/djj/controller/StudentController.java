package com.djj.controller;

import com.djj.entity.Clazz;
import com.djj.entity.Student;
import com.djj.entity.Subject;
import com.djj.entity.Teacher;
import com.djj.service.ClazzService;
import com.djj.service.StudentService;
import com.djj.service.SubjectService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final String LIST = "student/list";
    private static final String ADD = "student/add";
    private static final String UPDATE = "student/update";

    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClazzService clazzService;

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
    public Map<String, Object> create(@RequestBody Student student) {
        //设置学生的状态
        student.setStatus(Student.StatusType.type_1);
        int result = studentService.create(student);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = studentService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //删除操作
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = studentService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Student student) {
        int result = studentService.update(student);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap) {
        //查询出要修改的学生的信息
        Student student = studentService.detail(id);
        //查询所有的专业
        List<Subject> subjects = subjectService.query(null);
        //将查询出来的数据存储到request域，实现表单回显
        modelMap.addAttribute("student", student);
        modelMap.addAttribute("subjects", subjects);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Student student) {
        //查询所有的学生信息
        List<Student> list = studentService.query(student);
        //查询所有的专业
        List<Subject> subjects = subjectService.query(null);
        //查询所有的班级
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联
        list.forEach(entity -> {
            subjects.forEach(subject -> {
                //判断学生表中的subjectId和专业表的id是否一致
                if (subject.getId() == entity.getSubjectId()) {
                    entity.setSubject(subject);
                }
            });
            clazzes.forEach(clazz -> {
                //判断学生表中的clazzId和班级表的id是否一致
                if (clazz.getId() == entity.getClazzId()) {
                    entity.setClazz(clazz);
                }
            });
        });
        //查询总记录条数
        Integer count = studentService.count(student);
        return MapControl.getInstance().success().page(list, count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

    //跳转查询学生页面
    @GetMapping("/teacher_student")
    public String teacher_student(ModelMap modelMap, HttpSession session) {
        //查询所有的专业
        List<Subject> subjects = subjectService.query(null);
        //查询所有的班级
        List<Clazz> clazzes = clazzService.query(null);
        Teacher teacher = (Teacher) session.getAttribute("user");
        modelMap.addAttribute("subjects", subjects);
        modelMap.addAttribute("clazzes", clazzes);
        modelMap.addAttribute("teacher", teacher);
        return "student/teacher_student";
    }

    //老师查询学生
    @PostMapping("/teacher_student")
    @ResponseBody
    public Map<String, Object> teacher_student(Integer clazzId, Integer subjectId, ModelMap modelMap, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("user");
        List<Student> students = studentService.queryStudentByTeacher(teacher.getId(), clazzId, subjectId);
        List<Subject> subjects = subjectService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        //设置关联
        students.forEach(entity -> {
            subjects.forEach(subject -> {
                //判断学生表的subjectId和专业表的id是否一致
                if (subject.getId() == entity.getSubjectId()) {
                    entity.setSubject(subject);
                }
            });
            clazzes.forEach(clazz -> {
                //判断学生表的clazzId和班级表的id是否一致
                if (clazz.getId() == entity.getClazzId()) {
                    entity.setClazz(clazz);
                }
            });
        });
        return MapControl.getInstance().success().add("data", students).getMap();
    }
}
