/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg 
 * @author: ruopeng.sha   
 * @date: 2018-11-06 09:49
 */
package com.srct.plugin.module;

import java.util.HashMap;

import com.srct.plugin.BaseConfig;
import com.srct.plugin.utils.Utils;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;

import lombok.Data;

/**
 * @ClassName: ModuleConfig
 * @Description: TODO
 */
@Data
public class ModuleConfig extends BaseConfig {

	public final static String APPLICATION_COMMON_NAME = "StartApplication";

	// 项目名
	private String projectName;
	// 模块名
	private String moduleName;
	// 首字母大写模块名
	private String moduleNameFU;
	// 模块路径
	private String modulePath;
	// 模块资源路径
	private String resourePath;
	// common模块配置路径
	private String resoureConfigPath;
	// 基础包路径
	private String packagePath;
	// response 路径
	private String responsePath;
	// 多库切换配置路径
	private String dbConfigPath;
	

	// 基础包名
	private String packageName;
	// 启动类名称
	private String applicationName;
	// common 包名
	private String commonPackage;
	// response 包名
	private String responsePackage;
	// 多库切换配置包名
	private String dbConfigPackage;


	public ModuleConfig(String projectName, String moduleName) {
		this.projectName = projectName;
		this.moduleName = StringUtil.firstLetterUpper(moduleName);
		this.moduleNameFU = StringUtil.firstLetterUpper(moduleName);
		this.applicationName = Utils.firstToCapital(moduleName + APPLICATION_COMMON_NAME);

		this.modulePath = PROJECT_PATH + Utils.firstToCapital(projectName) + Utils.firstToCapital(moduleName) + sep;
		this.resourePath = this.modulePath + RESOURCES_PATH;
		this.resoureConfigPath = resourePath + "config" + sep;

		this.packageName = BASIC_PACKAGE + "." + projectName.toLowerCase() + "." + moduleName.toLowerCase();
		this.packagePath = Utils.packageConvertPath(packageName, modulePath + JAVA_PATH);

		this.commonPackage = BASIC_PACKAGE + "." + projectName.toLowerCase() + ".common";
		this.responsePackage = commonPackage + ".config.response";
		this.dbConfigPackage = commonPackage + ".config.db";
		
		String commonPath = PROJECT_PATH + Utils.firstToCapital(projectName) + "Common" + sep + JAVA_PATH;
		this.responsePath = Utils.packageConvertPath(responsePackage, commonPath);
		this.dbConfigPath = Utils.packageConvertPath(dbConfigPackage, commonPath);
		
		Utils.makeDir(this.modulePath + JAVA_PATH);
		Utils.makeDir(resourePath);
		Utils.makeDir(resoureConfigPath);
		Utils.makeDir(packagePath);
		Utils.makeDir(dbConfigPath);

		makeData();

		System.out.println(this.toString());
	}

	/**
	 * @Title: makeData
	 * @Description: TODO
	 * @param moduleConfig
	 *            void
	 */
	protected void makeData() {
		if (data == null) {
			data = new HashMap<>();
		}
		data = ReflectionUtil.getHashMap(this);
	}

	@Override
	public String toString() {
		return JSONUtil.toJSONString(data);
	}
}
