package com.djj.controller;

import com.djj.entity.Course;
import com.djj.entity.Score;
import com.djj.entity.Section;
import com.djj.entity.Student;
import com.djj.service.CourseService;
import com.djj.service.ScoreService;
import com.djj.service.SectionService;
import com.djj.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;

    //添加操作
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(String sectionIds, String courseIds, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        int result = scoreService.create(sectionIds, courseIds, student.getId());
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //修改操作
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Score score) {
        int result = scoreService.update(score);
        if (result <= 0) {
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    //根据id查询
    @PostMapping("/detail/{id}")
    @ResponseBody
    public Map<String, Object> detail(@PathVariable("id") Integer id) {
        Score score = scoreService.detail(id);
        if (score == null) {
            return MapControl.getInstance().nodata().getMap();
        }
        return MapControl.getInstance().success().put("data", score).getMap();
    }

    //查询所有
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(Score score) {
        List<Score> list = scoreService.query(score);
        return MapControl.getInstance().success().put("data", list).getMap();
    }

    //跳转查询成绩页面
    @GetMapping("/student_score")
    public String student_score() {
        return "score/student_score";
    }

    //查询学生成绩
    @PostMapping("/query_student_score")
    @ResponseBody
    public Map<String, Object> query_student_score(HttpSession session) {
        //从session中获取
        Student student = (Student) session.getAttribute("user");
        Score score = new Score();
        score.setStuId(student.getId());
        //查询成绩
        List<Score> scores = scoreService.query(score);
        //查询课程信息
        List<Course> courses = courseService.query(null);
        //查询开课信息
        List<Section> sections = sectionService.query(null);

        scores.forEach(entity -> {
            courses.forEach(course -> {
                //判断该成绩表中的courseId与课程表的id是否一致
                if (entity.getCourseId() == course.getId()) {
                    entity.setCourse(course);
                }
            });
            sections.forEach(section -> {
                //判断该成绩的开课id是否与开课的id一致
                if (entity.getSectionId() == section.getId()) {
                    entity.setSection(section);
                }
            });
            entity.setStudent(student);
        });
        return MapControl.getInstance().success().put("data", scores).getMap();
    }
}
