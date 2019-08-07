/**
 * Title: MsgVerificationServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 21:57
 * @description Project Name: Grote
 * @Package: com.srct.service.service.impl
 */
package com.srct.service.service.impl.sms;

import com.srct.service.cache.constant.MsgVerificationType;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.constant.ParamFrame;
import com.srct.service.service.sms.MsgVerificationService;
import com.srct.service.service.RedisTokenOperateService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.security.RandomUtil;
import com.srct.service.validate.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MsgVerificationServiceImpl implements MsgVerificationService {

    @Resource
    private RedisTokenOperateService tokenService;

    @Resource
    private FrameCacheService frameCacheService;

    @Override
    public String generateMsgCode(String token, MsgVerificationType type) {
        String code = RandomUtil.getUUIDNumber(4);
        Validator.assertNotBlank(token, ErrCodeSys.PA_DATA_NOT_EXIST, "token");
        tokenService.updateToken(token, type.name(), code);
        return code;
    }

    @Override
    public void validateMsgCode(String token, String code, MsgVerificationType type) {
        Validator.assertNotBlank(token, ErrCodeSys.PA_DATA_NOT_EXIST, "token");
        Validator.assertNotBlank(code, ErrCodeSys.PA_DATA_NOT_EXIST, "短信验证码");
        if (!frameCacheService.getParamSwitch(ParamFrame.TEST_MODE_GRAPH_VALIDATE_SWITCH)) {
            Object msgCode = tokenService.getToken(token, type.name());
            Validator.assertNotNull(msgCode, ErrCodeSys.SYS_ERR_MSG, "短信验证码已过期");
            Validator.assertMatch(msgCode.toString(), code.toLowerCase(), ErrCodeSys.SYS_ERR_MSG, "短信验证码错误");
        }
        tokenService.updateToken(token, type.name(), null);

    }
}
