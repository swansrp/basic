/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.bo 
 * @author: xu1223.zhang   
 * @date: 2018-08-06 16:26
 */
package com.srct.service.bo;

import com.srct.service.dao.po.AccountInfo;

/**
 * @ClassName: LoginResponseInfoBO
 * @Description: TODO
 */
public class LoginResponseInfoBO extends AccountInfo {

    private String accessToken;

    private String refreshToken;

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken
     *            the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken
     *            the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
