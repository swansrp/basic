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

import com.srct.service.utils.CommonEnum;

/**
 * @ClassName: TokenRole
 * @Description: TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenRole {

    int[] roles() default { CommonEnum.AccountRoleEnum.ACCOUNT_ROLE_ROOT,
            CommonEnum.AccountRoleEnum.ACCOUNT_ROLE_OTHERS };
}
