/**
 * Title: CaptchaType.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 21:31
 * @description Project Name: Grote
 * @Package: com.srct.service.cache.constant
 */
package com.srct.service.cache.constant;

import com.srct.service.constant.Dict;

public enum CaptchaType implements Dict {
    // 图形验证码类型
    CAPTCHA_TYPE,
    // 1-登录图形验证码
    LOGIN_CAPTCHA,
    // 2-登录短信验证码
    LOGIN_MSG_CAPTCHA,
    // 3-修改密码短信验证码
    FIND_PASSWORD_CAPTCHA
}
