package com.djj.controller;

import com.djj.entity.*;
import com.djj.service.*;
import com.djj.utils.MapControl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/section")
public class SectionController {

    private static final String LIST = "section/list";
    private static final String ADD = "section/add";
    private static final String UPDATE = "section/update";

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    public ScoreService scoreService;

    //跳转添加页面
    @GetMapping("/add")
    public String create(Integer clazzId, ModelMap modelMap) {
        //查询所有的老师，存储到request域
        List<Teacher> teachers = teacherService.query(null);
        //查询所有的课程，存储到request域
        List<Course> courses = courseService.query(null);
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("courses", courses);
        modelMap.addAttribute("clazzId", clazzId);
        return ADD;
    }

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Section section) {
        int result = sectionService.create(section);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id删除
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        int result = sectionService.delete(id);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //批量删除
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        int result = sectionService.delete(ids);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Section section) {
        int result = sectionService.update(section);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询，跳转修改页面
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, ModelMap modelMap) {
        //查询出要修改的班级
        Section section = sectionService.detail(id);
        //查询所有的老师
        List<Teacher> teachers = teacherService.query(null);
        //查询所有的课程
        List<Course> courses = courseService.query(null);
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("courses", courses);
        modelMap.addAttribute("section", section);
        return UPDATE;
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestBody Section section) {
        //查询所有的开课信息
        List<Section> list = sectionService.query(section);
        //查询所有的老师
        List<Teacher> teachers = teacherService.query(null);
        //查询所有的班级
        List<Course> courses = courseService.query(null);
        //设置关联
        list.forEach(entity -> {
            teachers.forEach(teacher -> {
                //判断开课表的teacherId和老师表的id是否一致
                if (teacher.getId() == entity.getTeacherId()) {
                    entity.setTeacher(teacher);
                }
            });
            courses.forEach(course -> {
                //判断开课表中的courseId和课程表的id是否一致
                if (course.getId() == entity.getCourseId()) {
                    entity.setCourse(course);
                }
            });
        });
        //查询宗记录条数
        Integer count = sectionService.count(section);
        return MapControl.getInstance().success().page(list, count).getMap();
    }

    //跳转列表页面
    @GetMapping("/list")
    public String list() {
        return LIST;
    }

    //生成zTree树形
    @PostMapping("/tree")
    @ResponseBody
    public List<Map> tree() {
        List<Subject> subjects = subjectService.query(null);
        List<Clazz> clazzes = clazzService.query(null);
        List<Map> list = new ArrayList<>();
        subjects.forEach(subject -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", subject.getId());
            map.put("name", subject.getSubjectName());
            map.put("parentId", 0);
            List<Map<String, Object>> childrenList = new ArrayList<>();
            clazzes.forEach(clazz -> {
                if (subject.getId() == clazz.getSubjectId()) {
                    Map<String, Object> children = new HashMap<>();
                    children.put("id", clazz.getId());
                    children.put("name", clazz.getClazzName());
                    children.put("parentId", subject.getId());
                    childrenList.add(children);
                }
            });
            map.put("children", childrenList);
            list.add(map);
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    //跳转开课信息列表页面
    @GetMapping("/student_section")
    public String student_section() {
        return "section/student_section";
    }

    @PostMapping("/query_student_section")
    @ResponseBody
    public Map<String, Object> student_section(HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        List<Section> sections = sectionService.queryByStudent(student.getId());
        List<Clazz> clazzes = clazzService.query(null);
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);

        sections.forEach(section -> {
            clazzes.forEach(clazz -> {
                if (section.getClazzId() == clazz.getId()) {
                    section.setClazz(clazz);
                }
            });
            teachers.forEach(teacher -> {
                if (section.getTeacherId() == teacher.getId()) {
                    section.setTeacher(teacher);
                }
            });
            courses.forEach(course -> {
                if (section.getCourseId() == course.getId()) {
                    section.setCourse(course);
                }
            });
        });
        return MapControl.getInstance().success().add("data", sections).getMap();
    }

    @GetMapping("/teacher_section")
    public String teacher_section() {
        return "section/teacher_section";
    }

    @PostMapping("/query_teacher_section")
    @ResponseBody
    public Map<String, Object> query_teacher_section(HttpSession session) {
        //获取登录老师的信息
        Teacher teacher = (Teacher) session.getAttribute("user");
        Section param = new Section();
        param.setTeacherId(teacher.getId());
        List<Section> sections = sectionService.query(param);
        List<Clazz> clazzes = clazzService.query(null);
        List<Course> courses = courseService.query(null);

        sections.forEach(section -> {

            clazzes.forEach(clazz -> {
                if (section.getClazzId() == clazz.getId().intValue()) {
                    section.setClazz(clazz);
                }
            });
            courses.forEach(course -> {
                if (section.getCourseId() == course.getId().intValue()) {
                    section.setCourse(course);
                }
            });

        });
        return MapControl.getInstance().success().add("data", sections).getMap();
    }

    @GetMapping("/teacher_student_score")
    public String teacher_student_score(Integer courseId, Integer sectionId, ModelMap modelMap) {
        List<HashMap> list = studentService.querySelectStudent(courseId, sectionId);
        modelMap.put("list", list);
        modelMap.put("sectionId", sectionId);
        modelMap.put("courseId", courseId);
        return "section/teacher_student_score";
    }

    @PostMapping("/teacher_student_score")
    @ResponseBody
    public Map<String, Object> teacher_student_score(Integer courseId, Integer sectionId, String stuIds, String scores) {

        int result = scoreService.update(courseId, sectionId, stuIds, scores);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }
}
