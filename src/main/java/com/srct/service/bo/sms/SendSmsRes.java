/**
 * Title: SendSmsRes.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:20
 * @description Project Name: Grote
 * @Package: com.srct.service.account.po.sms
 */
package com.srct.service.bo.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendSmsRes {
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "RequestId")
    private String requestId;
    @JsonProperty(value = "BizId")
    private String bizId;
    @JsonProperty(value = "Code")
    private String code;
}
