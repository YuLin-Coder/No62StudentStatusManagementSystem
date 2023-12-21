package com.djj.dao;

import com.djj.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Mapper
public interface StudentDao {

    //插入
    int create(Student pi);

    //删除
    int delete(Map<String, Object> paramMap);

    //修改
    int update(Map<String, Object> paramMap);

    //查询所有
    List<Student> query(Map<String, Object> paramMap);

    //明细查询（只返回一条数据）
    Student detail(Map<String, Object> paramMap);

    //查询总记录条数
    int count(Map<String, Object> paramMap);

    //查询选课的学生
    List<HashMap> querySelectStudent(Map<String, Object> paramMap);

    //按照老师查询学生
    List<Student> queryStudentByTeacher(Map<String, Object> paramMap);
}