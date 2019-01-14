/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg 
 * @author: ruopeng.sha   
 * @date: 2018-11-06 09:51
 */
package com.srct.plugin.utils;

import java.io.File;

import com.srct.plugin.BaseConfig;

/**
 * @ClassName: Utils
 * @Description: TODO
 */

public class Utils {

	// 首字母大写
	public static String firstToCapital(String str) {
		char[] chars = str.toCharArray();
		if (chars[0] >= 'a' && chars[0] <= 'z') {
			chars[0] = (char) (chars[0] - 32);
		}
		return new String(chars);
	}

	// 包名转路径
	public static String packageConvertPath(String packageName, String basePath) {
		return basePath
				+ String.format("%s",
						packageName.contains(".") ? packageName.replace(".", BaseConfig.sep) : packageName)
				+ BaseConfig.sep;
	}

	/**
	 * @Title: makeDir
	 * @param String
	 * @Description: TODO void
	 */
	public static void makeDir(String path) {
		File file = new File(path);
		if (!file.getAbsoluteFile().exists()) {
			file.getAbsoluteFile().mkdirs();
		}
	}
}
