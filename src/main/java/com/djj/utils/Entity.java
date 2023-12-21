package com.djj.utils;

import java.io.Serializable;

//分页工具类
public class Entity implements Serializable {

    private Integer page; //页码
    private Integer limit; //每页显示条数

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
