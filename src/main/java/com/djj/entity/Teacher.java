package com.djj.entity;

import com.djj.utils.Entity;

public class Teacher extends Entity {

    private Integer id;

    private String remark;

    private String teacherPwd;

    private String teacherName;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTeacherPwd() {
        return teacherPwd;
    }

    public void setTeacherPwd(String teacherPwd) {
        this.teacherPwd = teacherPwd;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", teacherPwd='" + teacherPwd + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}