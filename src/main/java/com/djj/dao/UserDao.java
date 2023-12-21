package com.djj.dao;

import com.djj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {

    //插入
    int create(User user);

    //删除
    int delete(Map<String, Object> map);

    //修改
    int update(Map<String, Object> map);

    //查询所有
    List<User> query(Map<String, Object> map);

    //明细查询（只返回一条数据）
    User detail(Map<String, Object> map);

    //查询总记录条数
    int count(Map<String, Object> map);
}
