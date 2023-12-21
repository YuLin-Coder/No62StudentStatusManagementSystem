package com.djj.service;

import com.djj.dao.StudentDao;
import com.djj.entity.Student;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MD5Utils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    //添加
    public int create(Student pi) {
        pi.setStuPwd(MD5Utils.getMD5(pi.getStuPwd())); //设置密码加密，再存储到数据库
        return studentDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return studentDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count代表的是删除的记录数
        for (String str : ids.split(",")) {
            count = studentDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Student student) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(student)).addId(student.getId()).getMap();
        return studentDao.update(map);
    }

    //查询
    public List<Student> query(Student student) {
        if (student != null && student.getPage() != null) {
            PageHelper.startPage(student.getPage(), student.getLimit());
        }
        return studentDao.query(BeanMapUtils.beanToMap(student));
    }

    //根据id查询
    public Student detail(Integer id) {
        return studentDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录数
    public int count(Student student) {
        return studentDao.count(BeanMapUtils.beanToMap(student));
    }

    //登录
    public Student login(String userName, String password) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("stuNo", userName)
                .add("stuPwd", password)
                .getMap();
        return studentDao.detail(map);
    }

    //查询选课的学生
    public List<HashMap> querySelectStudent(Integer courseId, Integer sectionId) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("courseId", courseId)
                .add("sectionId", sectionId)
                .getMap();
        return studentDao.querySelectStudent(map);
    }

    //按照老师查询学生信息
    public List<Student> queryStudentByTeacher(Integer teacherId, Integer clazzId, Integer subjectId) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("teacherId", teacherId)
                .add("clazzId", clazzId)
                .add("subjectId", subjectId)
                .getMap();
        return studentDao.queryStudentByTeacher(map);
    }
}
