package com.djj.service;

import com.djj.dao.CourseDao;
import com.djj.entity.Course;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    //添加
    public int create(Course pi) {
        return courseDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return courseDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count表示删除的记录条数
        for (String str : ids.split(",")) {
            count = courseDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Course course) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(course)).addId(course.getId()).getMap();
        return courseDao.update(map);
    }

    //查询
    public List<Course> query(Course course) {
        if (course != null && course.getPage() != null) {
            PageHelper.startPage(course.getPage(), course.getLimit());
        }
        return courseDao.query(BeanMapUtils.beanToMap(course));
    }

    //根据id查询
    public Course detail(Integer id) {
        return courseDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Course course) {
        return courseDao.count(BeanMapUtils.beanToMap(course));
    }

}
