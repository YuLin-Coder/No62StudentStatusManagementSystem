package com.djj.dao;

import com.djj.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface CourseDao {

    //插入
    int create(Course pi);

    //删除
    int delete(Map<String, Object> paramMap);

    //修改
    int update(Map<String, Object> paramMap);

    //查询所有
    List<Course> query(Map<String, Object> paramMap);

    //详细查询
    Course detail(Map<String, Object> paramMap);

    //查询总记录条数
    int count(Map<String, Object> paramMap);
}