/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.service 
 * @author: xu1223.zhang   
 * @date: 2018-08-06 14:32
 */
package com.srct.service.service;

import com.srct.service.bo.LoginRequestInfoBO;
import com.srct.service.bo.LoginResponseInfoBO;
import com.srct.service.bo.RegistRequestInfoBO;

/**
 * @ClassName: AccountService
 * @Description: TODO
 */
public interface AccountService {

    LoginResponseInfoBO login(LoginRequestInfoBO loginRequestInfoBO);

    int regist(RegistRequestInfoBO registRequestInfoBO);
}
