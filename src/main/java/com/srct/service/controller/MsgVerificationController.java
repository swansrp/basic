/**
 * Title: MsgVerificationController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-1 23:36
 * @description Project Name: Grote
 * @Package: com.srct.service.controller
 */
package com.srct.service.controller;

import com.srct.service.cache.constant.CaptchaType;
import com.srct.service.cache.constant.MsgVerificationType;
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.MsgVerificationService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.service.cache.FrameCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "短信验证码", tags = "短信验证码")
@RestController("MsgVerificationController")
@RequestMapping(value = "")
public class MsgVerificationController {

    @Resource
    private MsgVerificationService msgVerificationService;

    @Resource
    private FrameCacheService frameCacheService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private RedisTokenOperateService tokenService;

    @ApiOperation(value = "获取图形验证", notes = "利用token获取图形验证码")
    @RequestMapping(value = "/msgCode", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "token", value = "token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "msgCodeType", value = "1登录2找回密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "graphCode", value = "图形验证码", required = true)})
    public ResponseEntity<CommonResponse<String>.Resp> getMsgCode(@RequestParam(value = "token") String token,
            @RequestParam(value = "msgCodeType") String msgCodeType,
            @RequestParam(value = "graphCode") String graphCode) {
        MsgVerificationType msgVerificationType = MsgVerificationType
                .valueOf(frameCacheService.getDictItemId(MsgVerificationType.MSG_VERIFICATION_TYPE, msgCodeType));
        CaptchaType captchaType = getCaptchaTypeByMsgCodeType(msgVerificationType);
        captchaService.validateCaptcha(token, graphCode, captchaType);
        msgVerificationService.generateMsgCode(token, msgVerificationType);
        return CommonExceptionHandler.generateResponse("");
    }

    private CaptchaType getCaptchaTypeByMsgCodeType(MsgVerificationType msgVerificationType) {
        switch (msgVerificationType) {
            case LOGIN_MSG_CODE:
                return CaptchaType.LOGIN_CAPTCHA;
            case MSG_VERIFICATION_TYPE:
                return CaptchaType.LOGIN_MSG_CAPTCHA;
            case FIND_PASSWORD_MSG_CODE:
                return CaptchaType.FIND_PASSWORD_CAPTCHA;
            default:
                return CaptchaType.LOGIN_CAPTCHA;
        }
    }
}
