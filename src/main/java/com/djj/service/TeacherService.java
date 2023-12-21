package com.djj.service;

import com.djj.dao.TeacherDao;
import com.djj.entity.Teacher;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MD5Utils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    //添加
    public int create(Teacher pi) {
        //设置密码加密
        pi.setTeacherPwd(MD5Utils.getMD5(pi.getTeacherPwd()));
        return teacherDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return teacherDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0;
        for (String str : ids.split(",")) {
            count = teacherDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Teacher teacher) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(teacher)).addId(teacher.getId()).getMap();
        return teacherDao.update(map);
    }

    //查询
    public List<Teacher> query(Teacher teacher) {
        if (teacher != null && teacher.getPage() != null) {
            PageHelper.startPage(teacher.getPage(), teacher.getLimit());
        }
        return teacherDao.query(BeanMapUtils.beanToMap(teacher));
    }

    //根据id查询
    public Teacher detail(Integer id) {
        return teacherDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录数
    public int count(Teacher teacher) {
        return teacherDao.count(BeanMapUtils.beanToMap(teacher));
    }

    //登录
    public Teacher login(String userName, String password) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("teacherName", userName)
                .add("teacherPwd", password)
                .getMap();
        return teacherDao.detail(map);
    }


}
