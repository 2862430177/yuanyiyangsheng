package com.hewei.common.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @Description 专用于数据库sql的date类型日期转换
 * @Author hewei
 * @Date 2024/6/19 14:04
 */
public class SqlDateUtils{

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }
}
