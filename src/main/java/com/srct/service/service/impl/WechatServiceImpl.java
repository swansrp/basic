/**
 * Title: WechatServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service.impl
 * @author Sharp
 * @date 2019-01-30 21:39:12
 */
package com.srct.service.service.impl;

import com.srct.service.bo.wechat.OpenIdBO;
import com.srct.service.exception.ServiceException;
import com.srct.service.po.wechat.miniprogram.OpenIdRespPO;
import com.srct.service.po.wechat.platform.WechatMsgTemplatePO;
import com.srct.service.po.wechat.platform.WechatMsgTemplateRespPO;
import com.srct.service.po.wechat.platform.WechatPlatformAccessTokenRespPO;
import com.srct.service.po.wechat.platform.WechatPlatformUserListPO;
import com.srct.service.service.RedisService;
import com.srct.service.service.RestService;
import com.srct.service.service.WechatService;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Sharp
 */
@Service
public class WechatServiceImpl implements WechatService {

    private static final String ACCESS_TOKEN_KEY = "wechat_public_accessToken";

    @Autowired
    private RestService restService;

    @Value("${wechat.openid.url}")
    private String openIdUrl;

    @Value("${wechat.accesstoken.url}")
    private String publicAccessTokenUrl;

    @Value("${wechat.msgtemplate.url}")
    private String msgTemplateUrl;

    @Autowired
    private RedisService redisService;

    @Override
    public String getPublicAccessToken() {
        WechatPlatformAccessTokenRespPO po = redisService.get(ACCESS_TOKEN_KEY, WechatPlatformAccessTokenRespPO.class);
        if (po == null) {
            po = restService.get(publicAccessTokenUrl, WechatPlatformAccessTokenRespPO.class);
            redisService.set(ACCESS_TOKEN_KEY, 7000, po);
        }
        Log.ii(po);
        return po.getAccess_token();
    }

    @Override
    public void pushMsgTemplate(WechatMsgTemplatePO msgTemplate) {
        String accessToken = getPublicAccessToken();
        String url = msgTemplateUrl + accessToken;
        Log.ii(url);
        Log.ii(msgTemplate);
        WechatPlatformUserListPO userList = restService
                .get("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken,
                        WechatPlatformUserListPO.class);
        WechatMsgTemplateRespPO res = restService.post(url, WechatMsgTemplateRespPO.class, msgTemplate);
    }

    @Override
    public OpenIdBO getOpenId(String wechatCode) {
        // TODO Auto-generated method stub
        String url = openIdUrl + wechatCode;
        OpenIdRespPO po = restService.get(url, OpenIdRespPO.class);
        if (po.getErrcode() != null || po.getOpenid() == null) {
            throw new ServiceException(po.getErrmsg());
        }
        OpenIdBO bo = new OpenIdBO(po.getOpenid(), po.getSession_key());
        return bo;
    }

}
