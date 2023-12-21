package com.djj.dao;

import com.djj.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Mapper
public interface ScoreDao {

    //插入
    int create(Score pi);

    //删除
    int delete(Map<String, Object> paramMap);

    //修改
    int update(Map<String, Object> paramMap);

    //查询所有
    List<Score> query(Map<String, Object> paramMap);

    //明细查询（只返回一条数据）
    Score detail(Map<String, Object> paramMap);

    //查询总记录条数
    int count(Map<String, Object> paramMap);

    //查询各科平均成绩
    List<HashMap> queryAvgScoreBySection(Map<String, Object> paramMap);
}