package com.djj.service;

import com.djj.dao.SectionDao;
import com.djj.entity.Section;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectionService {

    @Autowired
    private SectionDao sectionDao;

    //添加
    public int create(Section pi) {
        return sectionDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return sectionDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0;
        for (String str : ids.split(",")) {
            count = sectionDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Section section) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(section)).addId(section.getId()).getMap();
        return sectionDao.update(map);
    }

    //查询
    public List<Section> query(Section section) {
        if (section != null && section.getPage() != null) {
            PageHelper.startPage(section.getPage(), section.getLimit());
        }
        return sectionDao.query(BeanMapUtils.beanToMap(section));
    }

    //根据id查询
    public Section detail(Integer id) {
        return sectionDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Section section) {
        return sectionDao.count(BeanMapUtils.beanToMap(section));
    }

    //按照学生查询班级开课
    public List<Section> queryByStudent(Integer studentId) {
        return sectionDao.queryByStudent(MapParameter.getInstance().add("studentId", studentId).getMap());
    }
}
