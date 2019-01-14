/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.utils 
 * @author: xxfore   
 * @date: 2018-05-29 15:25
 */
package com.srct.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CommonUtil
 * @Description: TODO
 */
public class CommonUtil {

    private CommonUtil() {

    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkMobileNumber(String mobileNumber) {
        if (mobileNumber.startsWith("86") && mobileNumber.length() == 13) {
            mobileNumber = mobileNumber.substring(2);
        }
        if (mobileNumber.startsWith("+86") && mobileNumber.length() == 14) {
            mobileNumber = mobileNumber.substring(3);
        }
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(
                    "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9])|(19[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isAccessTokenValid(Date accessTokenCreateAt) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - accessTokenCreateAt.getTime() < 1000 * 60 * 60 * 24 * 1) {
            return true;
        }
        return false;
    }

    public static boolean isTodayTimeRange(Date time) {
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // LocalDateTime localTime = LocalDateTime.parse(time, dtf);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localTime = LocalDateTime.ofInstant(time.toInstant(), zoneId);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        if (localTime.isBefore(endTime) && localTime.isAfter(startTime)) {
            return true;
        }
        return false;
    }

    public static Date getDate(Long time) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getMonth(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int month = c.get(Calendar.MONTH);
        return (month + 1);
    }

    public static Map<String, List<Date>> getTimeMapByDurationType(Date minDate, Date maxDate, int durationType)
            throws Exception {
        ArrayList<Date> resultmin = new ArrayList<>();
        ArrayList<Date> resultmax = new ArrayList<>();
        Map<String, List<Date>> monthMap = new HashMap<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        switch (durationType) {
        case CommonEnum.CommonTimeEnum.DURATION_TYPE_MONTH:
            min.set(Calendar.DAY_OF_MONTH, 1);
            min.set(Calendar.HOUR_OF_DAY, 0);
            min.set(Calendar.MINUTE, 0);
            break;
        case CommonEnum.CommonTimeEnum.DURATION_TYPE_WEEK:
            min.set(Calendar.HOUR_OF_DAY, 0);
            min.set(Calendar.MINUTE, 0);
            break;
        case CommonEnum.CommonTimeEnum.DURATION_TYPE_DAY:
            min.set(Calendar.MINUTE, 0);
            break;
        case CommonEnum.CommonTimeEnum.DURATION_TYPE_HOUR:
            break;
        default:
            throw new RuntimeException();
        }
        min.set(Calendar.SECOND, 0);
        min.set(Calendar.MILLISECOND, 0);
        if (durationType == CommonEnum.CommonTimeEnum.DURATION_TYPE_MONTH) {
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        }

        max.setTime(maxDate);
        if (durationType == CommonEnum.CommonTimeEnum.DURATION_TYPE_MONTH) {
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }

        Calendar curr = min;
        while (curr.before(max)) {
            resultmin.add(curr.getTime());
            switch (durationType) {
            case CommonEnum.CommonTimeEnum.DURATION_TYPE_MONTH:
                curr.add(Calendar.MONTH, 1);
                break;
            case CommonEnum.CommonTimeEnum.DURATION_TYPE_WEEK:
                curr.add(Calendar.WEEK_OF_MONTH, 1);
                break;
            case CommonEnum.CommonTimeEnum.DURATION_TYPE_DAY:
                curr.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case CommonEnum.CommonTimeEnum.DURATION_TYPE_HOUR:
                curr.add(Calendar.HOUR, 1);
                break;
            default:
                throw new RuntimeException();
            }

            curr.add(Calendar.MILLISECOND, -1);
            resultmax.add(curr.getTime());
            curr.add(Calendar.MILLISECOND, 1);
        }
        monthMap.put("min", resultmin);
        monthMap.put("max", resultmax);
        return monthMap;
    }

    /**
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
}
