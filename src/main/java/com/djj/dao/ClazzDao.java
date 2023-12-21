package com.djj.dao;

import com.djj.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface ClazzDao {

    //插入
    int create(Clazz pi);

    //删除
    int delete(Map<String, Object> paramMap);

    //修改
    int update(Map<String, Object> paramMap);

    //查询所有
    List<Clazz> query(Map<String, Object> paramMap);

    //明细查询（只返回一条数据）
    Clazz detail(Map<String, Object> paramMap);

    //查询总记录条数
    int count(Map<String, Object> paramMap);
}