/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.config.annotation 
 * @author: xu1223.zhang   
 * @date: 2018-08-23 09:51
 */
package com.srct.service.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanFieldAnnotation {

    /**
     * Annotation filed order
     * 
     * @return order
     */
    String title();
}