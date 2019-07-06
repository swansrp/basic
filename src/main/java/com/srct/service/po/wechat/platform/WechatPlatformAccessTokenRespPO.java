/**
 * Title: WechatPlatformAccessTokenRespPO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-20 22:33
 * @description Project Name: Tanya
 * Package: com.srct.service.po.wechat.platform
 */
package com.srct.service.po.wechat.platform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WechatPlatformAccessTokenRespPO {
    @JsonProperty("access_token")
    private String access_token;
    @JsonProperty("expires_in")
    private String expires_in;
}
