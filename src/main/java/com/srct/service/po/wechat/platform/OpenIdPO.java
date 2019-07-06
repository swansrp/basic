/**
 * Title: OpenIdPO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-21 0:16
 * @description Project Name: Tanya
 * Package: com.srct.service.po.wechat.platform
 */
package com.srct.service.po.wechat.platform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenIdPO {
    private List<String> openId;
}
