package com.djj.service;

import com.djj.dao.SubjectDao;
import com.djj.entity.Subject;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    //添加
    public int create(Subject pi) {
        return subjectDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return subjectDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //删除的数据条数
        for (String str : ids.split(",")) {
            count = subjectDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Subject subject) {
        Map<String, Object> map = MapParameter.getInstance()
                .add(BeanMapUtils.beanToMapForUpdate(subject))
                .addId(subject.getId()).getMap();
        return subjectDao.update(map);
    }

    //查询
    public List<Subject> query(Subject subject) {
        //分页查询
        if (subject != null && subject.getPage() != null) {
            PageHelper.startPage(subject.getPage(), subject.getLimit());
        }
        return subjectDao.query(BeanMapUtils.beanToMap(subject));
    }

    //根据id查询
    public Subject detail(Integer id) {
        return subjectDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Subject subject) {
        return subjectDao.count(BeanMapUtils.beanToMap(subject));
    }

}
