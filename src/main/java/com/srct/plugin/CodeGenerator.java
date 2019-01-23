/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.plugin.mbg 
 * @author: ruopeng.sha   
 * @date: 2018-11-03 11:08
 */
package com.srct.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.srct.plugin.mbg.BaseData;
import com.srct.plugin.mbg.MbgConfig;
import com.srct.plugin.module.ModuleConfig;
import com.srct.plugin.utils.DatabaseUtil;
import com.srct.service.utils.StringUtil;

import freemarker.template.TemplateExceptionHandler;

/**
 * @ClassName: CodeGenerator
 * @Description: TODO
 */
public class CodeGenerator {

    private static String projectName;

    private static Map<String, String> dbConfigMap;

    private static Map<String, String> dbConfigMapFL;

    private static Map<String, String> dbConfigMapPackageName;

    private static String projectNameforShort;

    public static void init(String projectName, String shortName, String dbNameList) {
        CodeGenerator.projectName = projectName;
        CodeGenerator.dbConfigMap = new HashMap<>();
        CodeGenerator.dbConfigMapPackageName = new HashMap<>();
        String[] nameList = dbNameList.split(",");
        for (String name : nameList) {
            CodeGenerator.dbConfigMap.put(name, StringUtil.firstUpperCamelCase(name));
            CodeGenerator.dbConfigMapPackageName.put(name, StringUtil.firstUpperCamelCase(name).toLowerCase());
        }
        CodeGenerator.projectNameforShort = shortName.toUpperCase();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入项目名称: ");
        String projectName = s.nextLine();
        System.out.println("请输入项目缩写: ");
        String alias = s.nextLine();
        System.out.println("请输入数据库名称(多库用','隔开): ");
        String dbNames = s.nextLine();
        init(projectName, alias, dbNames);
        genCommonModule();
        genModule("portal");
        genDBRelatedCode();
    }

    private static void genCommonModule() {
        ModuleConfig config = new ModuleConfig(projectName, "common");
        genPom(config, "pomCommon");
        genLogback(config);
        config.getData().put("projectNameFS", projectNameforShort);
        genResponse(config);
        config.getData().put("dbConfigMapPackageName", CodeGenerator.dbConfigMapPackageName);
        genProperties(config);
    }

    private static void genModule(String moduleName) {
        ModuleConfig config = new ModuleConfig(projectName, moduleName);
        genStarter(config);
        genPom(config, "pomModule");
    }

    private static void genStarter(ModuleConfig config) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getPackagePath() + config.getApplicationName() + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("StartApplication.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成" + config.getApplicationName() + "失败", e);
        }
    }

    private static void genPom(ModuleConfig config, String ftlName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getModulePath() + "pom.xml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate(ftlName + ".ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 pom 失败", e);
        }
    }

    private static void genResponse(ModuleConfig config) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(
                    config.getResponsePath() + config.getProjectName() + "ExceptionHandler" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("exceptionHandler.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 exceptionHandler 失败", e);
        }
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(
                    config.getResponsePath() + config.getProjectName() + "ResponseConstant" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("responseConstant.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 responseConstant 失败", e);
        }
    }

    private static void genLogback(ModuleConfig config) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getResourePath() + "logback-boot" + BaseConfig.XML_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("logback-boot.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 logback 失败", e);
        }
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getResourePath() + "prd-logback-boot" + BaseConfig.XML_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("prd-logback-boot.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 prd-logback 失败", e);
        }
    }

    private static void genProperties(ModuleConfig config) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getResoureConfigPath() + "application" + "" + ".properties");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                System.out.println(file.getAbsolutePath() + "已经存在不再覆盖");
            } else {
                cfg.getTemplate("commonProperties.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
                System.out.println(file.getAbsolutePath() + "生成成功");
            }
        } catch (Exception e) {
            throw new RuntimeException("生成 commonProperties 失败", e);
        }
        String[] envArray = { "-basetion", "-dev", "-devbastion", "-prod", "-stg", "-stress" };
        for (String env : envArray) {
            try {
                config.getData().put("env", env);
                freemarker.template.Configuration cfg = getConfiguration();
                File file = new File(config.getResoureConfigPath() + "application" + env + ".properties");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    System.out.println(file.getAbsolutePath() + "已经存在不再覆盖");
                    continue;
                }
                cfg.getTemplate("envProperties.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
                System.out.println(file.getAbsolutePath() + "生成成功");
            } catch (Exception e) {
                throw new RuntimeException("生成 application" + env + ".properties失败", e);
            }
        }
    }

    public static void genDBRelatedCode() {
        ModuleConfig commonConfig = new ModuleConfig(projectName, "common");
        commonConfig.getData().put("dbConfigMap", CodeGenerator.dbConfigMap);
        commonConfig.getData().put("dbConfigMapPackageName", CodeGenerator.dbConfigMapPackageName);
        genDBConfig(commonConfig);
        for (Entry<String, String> entry : dbConfigMap.entrySet()) {
            System.out.println("Start to genarator: " + entry.getKey());
            MbgConfig config = new MbgConfig(projectName, entry.getKey());
            List<String> tableNames = DatabaseUtil.getTableListFromDB(entry.getKey());
            for (String tableName : tableNames) {
                System.out.println(tableName);
                genCode(config, tableName);
                genModelAndMapper(config, config.getDbName(), tableName);
            }
        }
    }

    private static void genDBConfig(ModuleConfig commonConfig) {
        genDataSourceConfig(commonConfig);
        genDataSourceLifeCycle(commonConfig);
        genDataSrouceAspect(commonConfig);
    }

    private static void genDataSourceConfig(ModuleConfig commonConfig) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(commonConfig.getDbConfigPath() + "DataBaseConfig" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("dataSourceConfig.ftl", "UTF-8").process(commonConfig.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 DataBaseConfig 失败", e);
        }
    }

    private static void genDataSourceLifeCycle(ModuleConfig commonConfig) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(commonConfig.getDbConfigPath() + "DataSourceLifecycle" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("dataSourceLifecycle.ftl", "UTF-8").process(commonConfig.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 DataSourceLifecycle 失败", e);
        }
    }

    private static void genDataSrouceAspect(ModuleConfig commonConfig) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(commonConfig.getDbConfigPath() + "DynamicDataSourceAspect" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("dynamicDataSourceAspect.ftl", "UTF-8").process(commonConfig.getData(),
                    new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 DynamicDataSourceAspect 失败", e);
        }
    }

    private static void genCode(MbgConfig config, String tableName) {
        String modelName = StringUtil.firstUpperCamelCase(tableName);
        config.getData().put("modelName", modelName);
        config.getData().put("modelNameFL", StringUtil.firstLowerCamelCase(modelName));
        config.getData().put("modelNameURL", StringUtil.firstLowerCamelCase(modelName).toLowerCase());
        genRepository(config, modelName);
        genMapper(config, modelName);
        genVO(config, tableName);
        genController(config, modelName);
    }

    private static void genVO(MbgConfig config, String tableName) {
        List<BaseData> baseDataList = DatabaseUtil.getBaseDataList(config.getDbName(), tableName);
        config.getData().put("baseDataList", baseDataList);
        String modelName = StringUtil.firstUpperCamelCase(tableName);
        genEntityVO(config, modelName);
    }

    private static void genEntityVO(MbgConfig config, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getEntityVOPath() + modelName + "EntityVO" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("entityVO.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 Entity VO 失败", e);
        }
    }

    private static void genRepository(MbgConfig config, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getRepositoryPath() + modelName + "Dao" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                System.out.println(file.getAbsolutePath() + "已经存在不再覆盖");
                return;
            }
            cfg.getTemplate("repository.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 repository 失败", e);
        }
    }

    private static void genMapper(MbgConfig config, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getMapperPath() + modelName + "Mapper" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("mapper.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 mapper 失败", e);
        }
    }

    private static void genController(MbgConfig config, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            File file = new File(config.getControllerPath() + modelName + "Controller" + BaseConfig.JAVA_SUFFIX);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl", "UTF-8").process(config.getData(), new FileWriter(file));
            System.out.println(file.getAbsolutePath() + "生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成 controller 失败", e);
        }
    }

    public static void genModelAndMapper(MbgConfig config, String db, String tableName) {
        String modelName = StringUtil.firstUpperCamelCase(tableName);
        Context context = new Context(ModelType.FLAT);
        context.setId("SRC-T");
        context.setTargetRuntime("MyBatis3");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(DatabaseUtil.BASEURL + db + DatabaseUtil.PROFILE);
        jdbcConnectionConfiguration.setUserId(DatabaseUtil.USERNAME);
        jdbcConnectionConfiguration.setPassword(DatabaseUtil.PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(DatabaseUtil.DRIVER);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(config.getCommonPath());
        javaModelGeneratorConfiguration.setTargetPackage(config.getEntityPackage());
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(config.getCommonPath());
        sqlMapGeneratorConfiguration.setTargetPackage(config.getMapperPackage());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        /*
         * JavaClientGeneratorConfiguration javaClientGeneratorConfiguration =
         * new JavaClientGeneratorConfiguration();
         * javaClientGeneratorConfiguration.setTargetProject(config.
         * getCommonPath());
         * javaClientGeneratorConfiguration.setTargetPackage(config.
         * getMapperPackage());
         * javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
         * javaClientGeneratorConfiguration.addProperty("enableSubPackages",
         * "true"); context.setJavaClientGeneratorConfiguration(
         * javaClientGeneratorConfiguration) ;
         */
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        tableConfiguration.setCountByExampleStatementEnabled(true);
        tableConfiguration.setDeleteByExampleStatementEnabled(true);
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setSelectByExampleQueryId("true");
        tableConfiguration.setSelectByExampleStatementEnabled(true);
        tableConfiguration.setSelectByPrimaryKeyQueryId("true");
        tableConfiguration.setSelectByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setUpdateByExampleStatementEnabled(true);
        tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(true);
        context.addTableConfiguration(tableConfiguration);
        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration mbgConfig = new Configuration();
            mbgConfig.addContext(context);
            mbgConfig.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(mbgConfig, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }
        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (!modelName.isEmpty()) {
            modelName = StringUtil.firstUpperCamelCase(tableName);
        }
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(
                freemarker.template.Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File(BaseConfig.TEMPLATE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
}
