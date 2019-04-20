/**
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Package: com.srct.service.annotation
 * @author: xu1223.zhang
 * @date: 2018-08-02 16:44
 */
package com.srct.service.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    AuthType role() default AuthType.GUEST;

    enum AuthType {
        UNLOGIN,
        GUEST,
        USER
    }

}
