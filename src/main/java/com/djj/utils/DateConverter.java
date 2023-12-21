package com.djj.utils;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//自定义日期格式转换器
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            if (!StringUtils.hasLength(source) && source.indexOf(":") > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(source);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
