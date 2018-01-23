package com.makenv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_FORMAT);

    public static Date strToDate(String str) throws ParseException {
        return simpleDateFormat.parse(str);
    }

    public static String dateToStr(Date date) {
        return simpleDateFormat.format(date);
    }
}
