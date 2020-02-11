/**
 * Title: MsgVerificationType.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 21:53
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.constant
 */
package com.srct.service.cache.constant;

import com.srct.service.constant.Dict;

public enum MsgVerificationType implements Dict {

    // 短信验证码类型
    MSG_VERIFICATION_TYPE,
    // 1-登录确认验证码
    LOGIN_MSG_CODE,
    // 2-修改密码验证码
    FIND_PASSWORD_MSG_CODE,
    // 3-身份验证验证码
    AUTHENTICATION_MSG_CODE,
    // 4-登录异常验证码
    LOGIN_ILLEGAL_MSG_CODE,
    // 5-用户注册验证码
    REGISTER_MSG_CODE,
    // 6-信息变更验证码
    INFORMATION_CHANGE_MSG_CODE
}
