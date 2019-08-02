/**
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Package: com.srct.service.service.impl
 * @author: xu1223.zhang
 * @date: 2018-08-07 14:11
 */
package com.srct.service.service.impl;

import com.srct.service.constant.ErrCodeSys;
import com.srct.service.service.RedisService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.security.RandomUtil;
import com.srct.service.validate.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisTokenOperateServiceImpl implements RedisTokenOperateService {

    private static final String REDIS_ACCESSTOKEN_SUFFIX = "accessToken";

    private static final String REDIS_REFRESHTOKEN_SUFFIX = "refreshToken";

    private static final String REDIS_TOKEN = "Token:Token:";

    private static final String REDIS_ROLE_SUFFIX = "role";

    private static final int ACCESSTOKEN_EXPIRATIONTIME = 2 * 60 * 60;

    private static final int REFRESHTOKEN_EXPIRATIONTIME = 30 * 24 * 60 * 60;

    private static final int TOKEN_TIMEOUT = 3600;

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
        Map<String, Object> tokenMap = new HashMap();
        tokenMap.put("token", token);
        redisService.set(REDIS_TOKEN + token, TOKEN_TIMEOUT, tokenMap);
        return token;
    }

    @Override
    public Object getToken(String token, String key) {
        Map<String, Object> tokenMap = redisService.get(REDIS_TOKEN + token, Map.class);
        Validator.assertNotEmpty(tokenMap, ErrCodeSys.SYS_SESSION_TIME_OUT);
        return tokenMap.get(key);
    }

    @Override
    public void updateToken(String token, String key, Object obj) {
        Map<String, Object> tokenMap = redisService.get(REDIS_TOKEN + token, Map.class);
        tokenMap.put(key, obj);
        redisService.set(REDIS_TOKEN + token, TOKEN_TIMEOUT, tokenMap);
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
