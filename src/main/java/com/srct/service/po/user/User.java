/**  
* @Title: User.java 
* Copyright (c) 2019 Sharp. All rights reserved.
* @Project Name: SpringBootCommonLib
* @Package: com.srct.service.po.user
* @author Sharp
* @date 2019-01-31 14:59:14   
*/  
package com.srct.service.po.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
@AllArgsConstructor
public class User {
        
    private String username;

    private String password;

    private Boolean enabled;

    private Boolean expired;

    private Boolean locked;

    private Set<String> roles;
}
