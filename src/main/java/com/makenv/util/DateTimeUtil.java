package com.makenv.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_FORMAT);

    public static Date strToDate(String str) {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return simpleDateFormat.format(date);
    }
}
