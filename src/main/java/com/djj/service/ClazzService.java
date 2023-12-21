package com.djj.service;

import com.djj.dao.ClazzDao;
import com.djj.entity.Clazz;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    //添加
    public int create(Clazz pi) {
        return clazzDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return clazzDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count表示删除的记录条数
        for (String str : ids.split(",")) {
            count = clazzDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Clazz clazz) {
        return clazzDao.update(MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(clazz)).addId(clazz.getId()).getMap());
    }

    //查询
    public List<Clazz> query(Clazz clazz) {
        if (clazz != null && clazz.getPage() != null) {
            PageHelper.startPage(clazz.getPage(), clazz.getLimit());
        }
        return clazzDao.query(BeanMapUtils.beanToMap(clazz));
    }

    //根据id查询
    public Clazz detail(Integer id) {
        return clazzDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Clazz clazz) {
        return clazzDao.count(BeanMapUtils.beanToMap(clazz));
    }

}
