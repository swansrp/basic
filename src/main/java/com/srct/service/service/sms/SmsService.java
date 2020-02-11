package com.srct.service.service.sms;

import com.srct.service.bo.sms.SendSmsReq;

/**
 * Title: SmsService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:12
 * @description Project Name: Grote
 * @Package: com.srct.service.service
 */
public interface SmsService {

    /**
     * 发送短信
     * @param sendSmsReq
     */
    void sendSms(SendSmsReq sendSmsReq);
}
