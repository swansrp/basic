/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${dbConfigPackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${dbConfigPackage};

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import ${BASIC_PACKAGE}.config.db.DataSourceContextHolder;

@Aspect
@Component
public class DynamicDataSourceAspect {

<#list dbConfigMap?keys as db>	
	@Pointcut("execution(* ${commonPackage}.datalayer.${dbConfigMapPackageName[db]}.repository..*.*(..))")
	public void repository${dbConfigMap[db]}() {
        // Just a pointCut function
    }

	@Before("repository${dbConfigMap[db]}()")
	public void before${dbConfigMap[db]}() {
		DataSourceContextHolder.setDB("${dbConfigMapPackageName[db]}");
    }

	@After("repository${dbConfigMap[db]}()")
	public void after${dbConfigMap[db]}() {
        DataSourceContextHolder.clearDB();
    }

</#list>
}
