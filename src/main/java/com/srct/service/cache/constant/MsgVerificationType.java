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
    // 1-注册登录短信验证码
    LOGIN_MSG_CODE,
    // 2-找回密码短信验证码
    FIND_PASSWORD_MSG_CODE
}
