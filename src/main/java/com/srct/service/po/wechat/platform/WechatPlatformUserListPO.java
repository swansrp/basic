/**
 * Title: WechatPlatformUserListPO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-21 0:12
 * @description Project Name: Tanya
 * Package: com.srct.service.po.wechat.platform
 */
package com.srct.service.po.wechat.platform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WechatPlatformUserListPO {
    private Integer total;
    private Integer count;
    private OpenIdPO data;

}
