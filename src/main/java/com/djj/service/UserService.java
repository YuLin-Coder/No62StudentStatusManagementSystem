package com.djj.service;

import com.djj.dao.UserDao;
import com.djj.entity.User;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MD5Utils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //添加
    public int create(User pi) {
        //设置密码加密
        pi.setUserPwd(MD5Utils.getMD5(pi.getUserPwd()));
        return userDao.create(pi);
    }

    //删除
    public int delete(Integer id) {
        return userDao.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //批量删除
    public int delete(String ids) {
        int count = 0;
        for (String str : ids.split(",")) {
            count = userDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(User user) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(user)).addId(user.getId()).getMap();
        return userDao.update(map);
    }

    //查询所有
    public List<User> query(User user) {
        if (user != null && user.getPage() != null) {
            PageHelper.startPage(user.getPage(), user.getLimit());
        }
        return userDao.query(BeanMapUtils.beanToMap(user));
    }

    //根据id查询
    public User detail(Integer id) {
        return userDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //登录
    public User login(String userName, String password) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("userName", userName)
                .add("userPwd", password)
                .getMap();
        return userDao.detail(map);
    }

    //查询总记录数
    public int count(User user) {
        //将user对象转换成map集合
        return userDao.count(BeanMapUtils.beanToMap(user));
    }


}
