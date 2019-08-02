package com.srct.service.service;

import com.srct.service.config.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: AuthTokenService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-20 15:48
 * @description Project Name: Tanya
 * Package: com.srct.service.service
 */
public interface AuthTokenService {
    void setTokenHolder();

    void validate(HttpServletRequest request, HttpServletResponse response, Auth.AuthType authType);
}
