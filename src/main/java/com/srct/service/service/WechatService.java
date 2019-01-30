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

/**
 * @author Sharp
 *
 */
public interface WechatService {

    public OpenIdBO getOpenId(String wechatCode);
}
