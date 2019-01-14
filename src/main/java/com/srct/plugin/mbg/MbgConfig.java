/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg 
 * @author: ruopeng.sha   
 * @date: 2018-11-03 14:04
 */
package com.srct.plugin.mbg;

import java.util.HashMap;

import com.srct.plugin.BaseConfig;
import com.srct.plugin.utils.Utils;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;

import lombok.Data;

/**
 * @ClassName: mbgConfig
 * @Description: TODO
 */
@Data
public class MbgConfig extends BaseConfig {

	// 项目名称
	private String projectName;
	// 数据库名字
	private String dbName;
	// 数据库包名
	private String dbPackageName;
	// 模块common路径
	private String commonPath;
	// portal路径
	private String portalPath;
	// entity路径
	private String entityPath;
	// mapper 路径
	private String mapperPath;
	// xml 路径
	private String xmlPath;
	// repository路径
	private String repositoryPath;
	// controller路径
	private String controllerPath;
	// entityVO路径
	private String entityVOPath;

	// base包名
	private String commonPackage;
	// portal包名
	private String portalPackage;
	// entity包名
	private String entityPackage;
	// mapper包名
	private String mapperPackage;
	// xml包名
	private String xmlPackage;
	// repository包名
	private String repositoryPackage;
	// controller包名
	private String controllerPackage;
	// entityVO包名
	private String entityVOPackage;


	public MbgConfig(String projectName, String dbName) {

		this.projectName = projectName;
		this.dbName = dbName;
		this.dbPackageName = StringUtil.firstLowerCamelCase(dbName).toLowerCase();

		this.commonPackage = BASIC_PACKAGE + "." + projectName.toLowerCase() + ".common";
		this.portalPackage = BASIC_PACKAGE + "." + projectName.toLowerCase() + ".portal";

		this.entityPackage = commonPackage + ".datalayer." + dbPackageName + ".entity";
		this.mapperPackage = commonPackage + ".datalayer." + dbPackageName + ".mapper";
		this.xmlPackage = commonPackage + ".datalayer." + dbPackageName + ".mapper";
		this.repositoryPackage = commonPackage + ".datalayer." + dbPackageName + ".repository";
		this.controllerPackage = portalPackage + ".controller.admin." + dbPackageName;
		this.entityVOPackage = portalPackage + ".vo.admin." + dbPackageName;

		this.commonPath = PROJECT_PATH + Utils.firstToCapital(projectName) + "Common" + sep + JAVA_PATH;
		this.portalPath = PROJECT_PATH + Utils.firstToCapital(projectName) + "Portal" + sep + JAVA_PATH;
		this.entityPath = Utils.packageConvertPath(entityPackage, commonPath);
		this.mapperPath = Utils.packageConvertPath(mapperPackage, commonPath);
		this.xmlPath = Utils.packageConvertPath(xmlPackage, commonPath);
		this.repositoryPath = Utils.packageConvertPath(repositoryPackage, commonPath);
		this.controllerPath = Utils.packageConvertPath(controllerPackage, portalPath);
		this.entityVOPath = Utils.packageConvertPath(entityVOPackage, portalPath);

		Utils.makeDir(entityPath);
		Utils.makeDir(mapperPath);
		Utils.makeDir(xmlPath);
		Utils.makeDir(repositoryPath);
		Utils.makeDir(controllerPath);
		Utils.makeDir(entityVOPath);

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
