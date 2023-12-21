package com.djj.entity;

import com.djj.utils.Entity;

public class Subject extends Entity {

    private String college;

    private Integer id;

    private String remark;

    private String subjectName;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "college='" + college + '\'' +
                ", id=" + id +
                ", remark='" + remark + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}