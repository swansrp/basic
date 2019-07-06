/**
 * Title: WechatService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service
 * @author Sharp
 * @date 2019-01-30 21:35:06
 */
package com.srct.service.service;

import com.srct.service.bo.wechat.OpenIdBO;
import com.srct.service.po.wechat.platform.WechatMsgTemplatePO;

/**
 * @author Sharp
 */
public interface WechatService {

    OpenIdBO getOpenId(String wechatCode);

    /**
     * fetch wechat public accessToken
     *
     * @return AccessToken
     */
    String getPublicAccessToken();

    /**
     * push message Template
     *
     * @param msgTemplate msg template po
     */
    void pushMsgTemplate(WechatMsgTemplatePO msgTemplate);
}
