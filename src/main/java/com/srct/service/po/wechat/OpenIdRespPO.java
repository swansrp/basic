/**
 * Title: OpenIdRespPO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.po.wechat
 * @author Sharp
 * @date 2019-01-30 21:29:09
 */
package com.srct.service.po.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sharp
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OpenIdRespPO extends WechatBasePO {
    private String openid;
    private String session_key;
    private String unionid;

}
