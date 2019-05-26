/**
 * Title: WechatMsgTemplateRespPO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-20 22:42
 * @description Project Name: Tanya
 * Package: com.srct.service.po.wechat.platform
 */
package com.srct.service.po.wechat.platform;

import com.srct.service.po.wechat.WechatBasePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class WechatMsgTemplateRespPO extends WechatBasePO {
    private String msgid;
}
