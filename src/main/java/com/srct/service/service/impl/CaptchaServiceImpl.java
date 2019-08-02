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

import com.srct.service.cache.constant.CaptchaType;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.constant.ParamFrame;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.CaptchaUtil;
import com.srct.service.validate.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private RedisTokenOperateService tokenService;

    @Resource
    private FrameCacheService frameCacheService;

    @Override
    public BufferedImage generateCaptcha(String token, CaptchaType type) {
        CaptchaUtil tool = new CaptchaUtil();
        StringBuffer code = new StringBuffer();
        BufferedImage image = tool.genRandomCodeImage(code);
        Validator.assertNotBlank(token, ErrCodeSys.PA_DATA_NOT_EXIST, "token");
        tokenService.updateToken(token, type.name(), code.toString().toLowerCase());
        return image;
    }

    @Override
    public BufferedImage generateCaptcha(String token) {
        return generateCaptcha(token, CaptchaType.LOGIN_CAPTCHA);

    }

    @Override
    public void validateCaptcha(String token, String code, CaptchaType type) {
        Validator.assertNotBlank(token, ErrCodeSys.PA_DATA_NOT_EXIST, "token");
        Validator.assertNotBlank(code, ErrCodeSys.PA_DATA_NOT_EXIST, "图形验证码");
        if (!frameCacheService.getParamSwitch(ParamFrame.TEST_MODE_GRAPH_VALIDATE_SWITCH)) {
            Object captcha = tokenService.getToken(token, type.name());
            Validator.assertNotNull(captcha, ErrCodeSys.SYS_ERR_MSG, "图形验证码已过期");
            Validator.assertMatch(captcha.toString(), code.toLowerCase(), ErrCodeSys.SYS_ERR_MSG, "验证码错误");
        }
        tokenService.updateToken(token, type.name(), null);
    }

    @Override
    public void validateCaptcha(String token, String code) {
        validateCaptcha(token, code, CaptchaType.LOGIN_CAPTCHA);
    }
}
