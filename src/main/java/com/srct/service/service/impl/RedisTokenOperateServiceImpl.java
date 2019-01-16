/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.service.impl 
 * @author: xu1223.zhang   
 * @date: 2018-08-07 14:11
 */
package com.srct.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.CommonEnum.AccountRoleEnum;
import com.srct.service.utils.CommonUtil;

/**
 * @ClassName: RedisTokenOperateServiceImpl
 * @Description: TODO
 */
@Service
public class RedisTokenOperateServiceImpl implements RedisTokenOperateService {

    private static final String REDIS_ACCESSTOKEN_SUFFIX = "accessToken";

    private static final String REDIS_REFRESHTOKEN_SUFFIX = "refreshToken";

    private static final String REDIS_ROLE_SUFFIX = "role";

    private static final int ACCESSTOKEN_EXPIRATIONTIME = 2 * 60 * 60;

    private static final int REFRESHTOKEN_EXPIRATIONTIME = 30 * 24 * 60 * 60;

    @Autowired
    RedisService redisService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.service.RedisTokenOperateService#setAccessToken(java.
     * lang. String)
     */
    @Override
    public void setAccessToken(Integer uid, String accessToken) {
        redisService.setex(uid + REDIS_ACCESSTOKEN_SUFFIX, ACCESSTOKEN_EXPIRATIONTIME, accessToken);
        redisService.setex(accessToken, ACCESSTOKEN_EXPIRATIONTIME, String.valueOf(uid));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.service.RedisTokenOperateService#getAccessToken()
     */
    @Override
    public String getAccessToken(Integer uid) {
        return redisService.get(uid + REDIS_ACCESSTOKEN_SUFFIX);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.service.RedisTokenOperateService#setRefreshToken(java.
     * lang. String)
     */
    @Override
    public void setRefreshToken(Integer uid, String refreshToken) {
        redisService.setex(uid + REDIS_REFRESHTOKEN_SUFFIX, REFRESHTOKEN_EXPIRATIONTIME, refreshToken);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.service.RedisTokenOperateService#getRefreshToken(java.
     * lang. Integer)
     */
    @Override
    public String getRefreshToken(Integer uid) {
        return redisService.get(uid + REDIS_REFRESHTOKEN_SUFFIX);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.service.RedisTokenOperateService#setUserRole(java.lang.
     * Integer, com.srct.service.utils.CommonEnum.AccountRoleEnum)
     */
    @Override
    public void setUserRole(Integer uid, Integer role) {
        redisService.set(uid + REDIS_ROLE_SUFFIX, String.valueOf(role));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.service.RedisTokenOperateService#getUserRole(java.lang.
     * Integer)
     */
    @Override
    public String getUserRole(Integer uid) {
        return redisService.get(uid + REDIS_ROLE_SUFFIX);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.service.RedisTokenOperateService#getUid(java.lang.
     * String)
     */
    @Override
    public String getUid(String accessToken) {
        return redisService.get(accessToken);
    }
}
