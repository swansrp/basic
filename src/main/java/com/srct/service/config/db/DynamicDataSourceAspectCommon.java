/**
 * Project Name:SpringBootCommon
 * File Name:DynamicDataSourceAspect.java
 * Package Name:com.srct.service.config.db
 * Date:2018年4月26日上午11:53:46
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 */
package com.srct.service.config.db;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ClassName:DynamicDataSourceAspect <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月26日 上午11:53:46 <br/>
 *
 * @author ruopeng.sha
 * @see
 * @since JDK 1.8
 */

public class DynamicDataSourceAspectCommon {

    @Pointcut("execution(public * com.srct.service.service..*.*(..))")
    public void callSecurityMapper() {
        // Just a pointCut function
    }

    @Before("callSecurityMapper()")
    public void before() {
        DataSourceContextHolder.setDB(DataSourceEnumCommon.TESTDB);
    }

    @After("callSecurityMapper()")
    public void after() {
        DataSourceContextHolder.clearDB();
    }
}
