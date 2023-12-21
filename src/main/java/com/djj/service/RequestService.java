package com.djj.service;

import com.djj.dao.RequestDao;
import com.djj.entity.Request;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    //添加
    public int create(Request pi) {
        return requestDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return requestDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count表示删除的记录条数
        for (String str : ids.split(",")) {
            count = requestDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Request request) {
        return requestDao.update(MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(request)).addId(request.getId()).getMap());
    }

    //查询
    public List<Request> query(Request request) {
        if (request != null && request.getPage() != null) {
            PageHelper.startPage(request.getPage(), request.getLimit());
        }
        return requestDao.query(BeanMapUtils.beanToMap(request));
    }

    //根据id查询
    public Request detail(Integer id) {
        return requestDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Request request) {
        return requestDao.count(BeanMapUtils.beanToMap(request));
    }

}
