package com.djj.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapControl {

    //目标对象
    private Map<String, Object> paramMap = new HashMap<>();

    //私有构造
    private MapControl() {

    }

    public static MapControl getInstance() {
        return new MapControl();
    }

    public MapControl add(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public MapControl success() {
        paramMap.put("code", Code.SUCCESS.getCode());
        paramMap.put("msg", Code.SUCCESS.getMsg());
        return this;
    }

    public MapControl error() {
        paramMap.put("code", Code.ERROR.getCode());
        paramMap.put("msg", Code.ERROR.getMsg());
        return this;
    }

    public MapControl error(String msg) {
        paramMap.put("code", Code.ERROR.getCode());
        paramMap.put("msg", msg);
        return this;
    }

    public MapControl notEmpty() {
        paramMap.put("code", Code.NOT_EMPTY.getCode());
        paramMap.put("msg", Code.NOT_EMPTY.getMsg());
        return this;
    }

    public MapControl nodata() {
        paramMap.put("code", Code.NODATA.getCode());
        paramMap.put("msg", Code.NODATA.getMsg());
        return this;
    }

    public MapControl page(List<?> list, Integer count) {
        paramMap.put("data", list);
        paramMap.put("count", count);
        return this;
    }

    public MapControl put(String key, Object value) {
        this.add(key, value);
        return this;
    }

    public MapControl addId(Object value) {
        paramMap.put("id", value);
        return this;
    }

    public MapControl add(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            paramMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public MapControl put(Map<String, Object> map) {
        return this.add(map);
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
