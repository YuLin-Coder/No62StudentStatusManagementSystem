package com.djj.dao;

import com.djj.entity.Section;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface SectionDao {

    //插入
    int create(Section pi);

    //删除
    int delete(Map<String, Object> paramMap);

    //修改
    int update(Map<String, Object> paramMap);

    //查询所有
    List<Section> query(Map<String, Object> paramMap);

    //明细查询（只返回一条数据）
    Section detail(Map<String, Object> paramMap);

    //查询总记录条数
    int count(Map<String, Object> paramMap);

    //按照学生查询班级开课
    List<Section> queryByStudent(Map<String, Object> paramMap);
}