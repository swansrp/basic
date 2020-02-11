/**
 * Title: SendSmsReq.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:13
 * @description Project Name: Grote
 * @Package: com.srct.service.account.po.sms
 */
package com.srct.service.bo.sms;

import com.srct.service.cache.constant.MsgVerificationType;
import lombok.Data;

import java.util.Map;

@Data
public class SendSmsReq {
    private String phoneNumbers;
    private MsgVerificationType sendSmsType;
    private Map<String, String> paramMap;
}
