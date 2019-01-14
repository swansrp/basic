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

/** 
 * @ClassName: StringUtil 
 * @Description: TODO 
 */

public class StringUtil {
    
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

    public static String firstLetterUpper(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.toLowerCase();
            return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        }
        return str;
    }


	public static String UppserForShort(String str) {
		String res = "";
		if (StringUtils.isNotBlank(str)) {
			String[] ss = str.split("(?<!^)(?=[A-Z])");
			for(String s:ss) {
				res += s;
			}
		}
		if(res.length()<3) {
			res = str.substring(0, 3).toUpperCase();
		}
		return res;
	}
}
