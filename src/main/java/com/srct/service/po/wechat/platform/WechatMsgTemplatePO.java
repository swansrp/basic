/**
 * Title: WecahtMsgTemplatePO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-20 22:21
 * @description Project Name: Tanya
 * Package: com.srct.service.po.wechat
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
public class WechatMsgTemplatePO {
    private String touser;
    @JsonProperty("template_id")
    private String template_id;
    private String url;
    private MiniProgramInfo miniprogram;
    private WechatMsgTemplateKey data;
}
