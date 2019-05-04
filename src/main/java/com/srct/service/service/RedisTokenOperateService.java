/**
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Package: com.srct.service.service
 * @author: xu1223.zhang
 * @date: 2018-08-07 14:09
 */
package com.srct.service.service;

/**
 * @ClassName: RedisTokenOperateService
 * @Description: TODO
 */
public interface RedisTokenOperateService {

    void setAccessToken(Integer uid, String accessToken);

    String getAccessToken(Integer uid);

    String getUid(String accessToken);

    void setRefreshToken(Integer uid, String refreshToken);

    String getRefreshToken(Integer uid);

    void setUserRole(Integer uid, Integer role);

    String getUserRole(Integer uid);

    String fetchToken();

    Object getToken(String token, String key);

    void updateToken(String token, String key, Object obj);

    void delToken(String token);
}
