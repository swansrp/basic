/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.utils
 * @author: ruopeng.sha
 * @date: 2018-11-06 14:04
 */
package com.srct.service.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil
 * @Description: TODO
 */
public class StringUtil {

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String SPLITER = "###";
    public static final String COMMA = ",";
    public static final String DOT = ".";

    public static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";

    private static final String YES = "1";
    private static final String NO = "0";

    public static String UppserForShort(String str) {
        String res = "";
        if (StringUtils.isNotBlank(str)) {
            String[] ss = str.split("(?<!^)(?=[A-Z])");
            for (String s : ss) {
                res += s;
            }
        }
        if (res.length() < 3) {
            res = str.substring(0, 3).toUpperCase();
        }
        return res;
    }

    public static String cleanScriptFormat(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        Pattern pattern = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }

    public static String convertSwitch(boolean bool) {
        return bool ? YES : NO;
    }

    public static boolean convertSwitch(String str) {
        return YES.equals(str);
    }

    public static boolean equals(String a, String b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.equals(b);
        }
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null || b == null) {
            return false;
        } else {
            return a.equalsIgnoreCase(b);
        }
    }

    public static String firstLetterUpper(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.toLowerCase();
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str;
    }

    public static String firstLowerCamelCase(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] strs = str.split("_");
            if (strs.length == 1) {
                return str.substring(0, 1).toLowerCase() + str.substring(1);
            } else {
                String convertedStr = "";
                for (int i = 0; i < strs.length; i++) {
                    convertedStr += firstLetterUpper(strs[i]);
                }
                return convertedStr.substring(0, 1).toLowerCase() + convertedStr.substring(1);
            }
        }
        return str;
    }

    public static String firstUpperCamelCase(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] strs = str.split("_");
            if (strs.length == 1) {
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                String convertedStr = "";
                for (int i = 0; i < strs.length; i++) {
                    convertedStr += firstLetterUpper(strs[i]);
                }
                return convertedStr.substring(0, 1).toUpperCase() + convertedStr.substring(1);
            }
        }
        return str;
    }

    public static String join(String... str) {
        return joinWith(SPLITER, str);
    }

    public static String joinWith(String spliter, String... str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(spliter).append(s);
        }
        return sb.toString().replaceFirst(spliter, "");
    }

    public static String joinWith(String spliter, List<String> list) {
        StringBuilder sb = new StringBuilder();
        String[] strArray = new String[list.size()];
        list.toArray(strArray);
        return joinWith(spliter, strArray);
    }

    public static List<String> split(String str, String spliter) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strArr = str.split(spliter);
        if (strArr.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String s : strArr) {
            if (StringUtils.isNotBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }
}
