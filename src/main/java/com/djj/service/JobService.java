package com.djj.service;

import com.djj.dao.JobDao;
import com.djj.entity.Job;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    //添加
    public int create(Job pi) {
        return jobDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return jobDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count表示删除的记录条数
        for (String str : ids.split(",")) {
            count = jobDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Job job) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(job)).addId(job.getId()).getMap();
        return jobDao.update(map);
    }

    //查询
    public List<Job> query(Job job) {
        if (job != null && job.getPage() != null) {
            PageHelper.startPage(job.getPage(), job.getLimit());
        }
        return jobDao.query(BeanMapUtils.beanToMap(job));
    }

    //根据id查询
    public Job detail(Integer id) {
        return jobDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Job job) {
        return jobDao.count(BeanMapUtils.beanToMap(job));
    }

}
