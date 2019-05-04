/**
 * Title: CaptchaServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-25 16:28
 * @description Project Name: Tanya
 * Package: com.srct.service.service.impl
 */
package com.srct.service.service.impl;

import com.srct.service.exception.ServiceException;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    final static private String tokenItem = "captcha";

    @Autowired
    private RedisTokenOperateService tokenService;

    @Override
    public BufferedImage generateCaptcha(String token) {
        CaptchaUtil tool = new CaptchaUtil();
        StringBuffer code = new StringBuffer();
        BufferedImage image = tool.genRandomCodeImage(code);
        if (token != null) {
            tokenService.updateToken(token, tokenItem, code.toString().toLowerCase());
        } else {
            throw new ServiceException("请先获取token");
        }
        return image;

    }

    @Override
    public void validateCaptcha(String token, String code) {
        if (token != null && code != null && code.toLowerCase().equals(tokenService.getToken(token, tokenItem))) {
            tokenService.updateToken(token, tokenItem, null);
        } else {
            throw new ServiceException("验证码不正确");
        }
    }
}
