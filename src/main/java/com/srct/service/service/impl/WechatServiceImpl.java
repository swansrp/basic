/**
 * Title: WechatServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.service.impl
 * @author Sharp
 * @date 2019-01-30 21:39:12
 */
package com.srct.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.srct.service.bo.wechat.OpenIdBO;
import com.srct.service.exception.ServiceException;
import com.srct.service.po.wechat.OpenIdRespPO;
import com.srct.service.service.RestService;
import com.srct.service.service.WechatService;

/**
 * @author Sharp
 *
 */
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    RestService restService;

    @Autowired
    @Value("${wechat.openid.url}")
    String openIdUrl;

    /* (non-Javadoc)
     * @see com.srct.service.service.WechatService#getOpenId(java.lang.String)
     */
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
