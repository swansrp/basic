/**
 * Title: MsgVerificationService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 21:52
 * @description Project Name: Grote
 * @Package: com.srct.service.service
 */
package com.srct.service.service;

import com.srct.service.cache.constant.MsgVerificationType;

public interface MsgVerificationService {

    /**
     * Generate msg code string.
     *
     * @param token the token
     * @param type  the type
     * @return the string
     */
    String generateMsgCode(String token, MsgVerificationType type);

    /**
     * Validate msg code.
     *
     * @param token the token
     * @param code  the code
     * @param type  the type
     */
    void validateMsgCode(String token, String code, MsgVerificationType type);
}
