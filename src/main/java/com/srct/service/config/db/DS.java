/**  
 * Project Name:SpringBootCommon  
 * File Name:DS.java  
 * Package Name:com.srct.service.config.db  
 * Date:2018年4月26日上午11:35:22  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.config.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:DS <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月26日 上午11:35:22 <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface DS {

    String value() default DataSourceEnumCommon.TESTDB;
}
