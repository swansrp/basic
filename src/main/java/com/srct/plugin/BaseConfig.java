/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin 
 * @author: ruopeng.sha   
 * @date: 2018-11-06 13:15
 */
package com.srct.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @ClassName: BaseConfig
 * @Description: TODO
 */
@Data
public class BaseConfig {

    public final static String author = System.getProperty("user.name");

    public final static String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    public final static String sep = System.getProperty("file.separator");

    // 公共项目路径
    public final static String BASE_PROJECT_PATH = System.getProperty("user.dir") + sep;

    // 资源路径
    public final static String RESOURCES_PATH = ("src/main/resources/").replace("/", sep);

    // 公共项目资源路径
    public final static String BASE_RESOURCES_PATH = BASE_PROJECT_PATH + RESOURCES_PATH;

    // 模板文件路径
    public final static String TEMPLATE_PATH = (BASE_RESOURCES_PATH + "generatorTemplate/").replace("/", sep);

    // 项目在硬盘上的基础路径
    public final static String PROJECT_PATH = BASE_PROJECT_PATH.substring(0,
            BASE_PROJECT_PATH.substring(0, BASE_PROJECT_PATH.length() - 1).lastIndexOf(sep)) + sep;

    // java文件路径
    public final static String JAVA_PATH = ("src/main/java/").replace("/", sep);

    // 基础包名
    public final static String BASIC_PACKAGE = "com.srct.service";

    public final static String JAVA_SUFFIX = ".java";

    public final static String XML_SUFFIX = ".xml";

    protected Map<String, Object> data = new HashMap<>();
}
