package com.djj.service;

import com.djj.dao.ScoreDao;
import com.djj.entity.Score;
import com.djj.utils.BeanMapUtils;
import com.djj.utils.MapParameter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    //添加
    public int create(String sectionIds, String courseIds, Integer studentId) {
        //清除已有选课数据
        Map<String, Object> map = MapParameter.getInstance().add("stuId", studentId).getMap();
        scoreDao.delete(map);
        //批量保存
        int flag = 0;
        String[] sectionIdArr = sectionIds.split(",");
        String[] courseIdArr = courseIds.split(",");
        for (int i = 0; i < sectionIdArr.length; i++) {
            Score score = new Score();
            score.setCourseId(Integer.parseInt(courseIdArr[i]));
            score.setSectionId(Integer.parseInt(sectionIdArr[i]));
            score.setStuId(studentId);
            flag = scoreDao.create(score);
        }
        return flag;
    }

    //批量删除
    public int delete(String ids) {
        int count = 0; //count表示删除的记录条数
        for (String str : ids.split(",")) {
            count = scoreDao.delete(MapParameter.getInstance().addId(Integer.parseInt(str)).getMap());
        }
        return count;
    }

    //修改
    public int update(Score score) {
        Map<String, Object> map = MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(score)).addId(score.getId()).getMap();
        return scoreDao.update(map);
    }

    //查询
    public List<Score> query(Score score) {
        if (score != null && score.getPage() != null) {
            PageHelper.startPage(score.getPage(), score.getLimit());
        }
        return scoreDao.query(BeanMapUtils.beanToMap(score));
    }

    //根据id查询
    public Score detail(Integer id) {
        return scoreDao.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询总记录条数
    public int count(Score score) {
        return scoreDao.count(BeanMapUtils.beanToMap(score));
    }

    //老师评分，修改成绩
    public int update(Integer courseId, Integer sectionId, String stuIds, String scores) {

        String[] stuIdArray = stuIds.split(",");
        String[] scoresArray = scores.split(",");
        int flag = 0;
        for (int i = 0; i < stuIdArray.length; i++) {
            Map<String, Object> map = MapParameter.getInstance()
                    .add("courseId", courseId)
                    .add("sectionId", sectionId)
                    .add("stuId", Integer.parseInt(stuIdArray[i]))
                    .add("updateScore", Double.parseDouble(scoresArray[i]))
                    .getMap();
            flag = scoreDao.update(map);
        }
        return flag;
    }

    //查询各科平均成绩
    public List<HashMap> queryAvgScoreBySection() {
        List<HashMap> mapList = scoreDao.queryAvgScoreBySection(null);
        return mapList;
    }
}
