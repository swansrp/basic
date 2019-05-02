/**
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Package: com.srct.service.service.impl
 * @author: xu1223.zhang
 * @date: 2018-08-07 14:11
 */
package com.srct.service.service.impl;

import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.security.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisTokenOperateServiceImpl implements RedisTokenOperateService {

    private static final String REDIS_ACCESSTOKEN_SUFFIX = "accessToken";

    private static final String REDIS_REFRESHTOKEN_SUFFIX = "refreshToken";

    private static final String REDIS_TOKEN = "token:";

    private static final String REDIS_ROLE_SUFFIX = "role";

    private static final int ACCESSTOKEN_EXPIRATIONTIME = 2 * 60 * 60;

    private static final int REFRESHTOKEN_EXPIRATIONTIME = 30 * 24 * 60 * 60;

    private static final int TOEKN_TIMEOUT = 3600;

    @Autowired
    RedisService redisService;

    @Override
    public void setAccessToken(Integer uid, String accessToken) {
        redisService.setex(uid + REDIS_ACCESSTOKEN_SUFFIX, ACCESSTOKEN_EXPIRATIONTIME, accessToken);
        redisService.setex(accessToken, ACCESSTOKEN_EXPIRATIONTIME, String.valueOf(uid));
    }

    @Override
    public String getAccessToken(Integer uid) {
        return redisService.get(uid + REDIS_ACCESSTOKEN_SUFFIX, String.class);
    }

    @Override
    public void setRefreshToken(Integer uid, String refreshToken) {
        redisService.setex(uid + REDIS_REFRESHTOKEN_SUFFIX, REFRESHTOKEN_EXPIRATIONTIME, refreshToken);
    }

    @Override
    public String getRefreshToken(Integer uid) {
        return redisService.get(uid + REDIS_REFRESHTOKEN_SUFFIX, String.class);
    }

    @Override
    public void setUserRole(Integer uid, Integer role) {
        redisService.set(uid + REDIS_ROLE_SUFFIX, String.valueOf(role));
    }

    @Override
    public String getUserRole(Integer uid) {
        return redisService.get(uid + REDIS_ROLE_SUFFIX, String.class);
    }

    @Override
    public String fetchToken() {
        String token = RandomUtil.getUUID();
        redisService.set(REDIS_TOKEN + token, TOEKN_TIMEOUT, token);
        return token;
    }

    @Override
    public Object getToken(String token) {
        return redisService.get(REDIS_TOKEN + token, Object.class);
    }

    @Override
    public void updateToken(String token, Object obj) {
        redisService.set(REDIS_TOKEN + token, TOEKN_TIMEOUT, obj);
    }

    @Override
    public void delToken(String token) {
        redisService.delete(REDIS_TOKEN + token);
    }

    @Override
    public String getUid(String accessToken) {
        return redisService.get(accessToken, String.class);
    }
}
