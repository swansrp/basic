/**  
 * Project Name:SpringBootCommon  
 * File Name:ResponseCode.java  
 * Package Name:com.srct.service.config.response  
 * Date:Apr 26, 2018 7:45:09 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.config.response;

import org.springframework.http.HttpStatus;

/**
 * ClassName:ResponseCode <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 26, 2018 7:45:09 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class CommonResponseCode {

    private String resultCode;

    private String resultMessage;

    private HttpStatus httpStatus;

    public CommonResponseCode(String code, String msg, HttpStatus status) {
        this.resultCode = code;
        this.resultMessage = msg;
        this.httpStatus = status;
    }

    /**
     * resultCode.
     * 
     * @return the resultCode
     * @since JDK 1.8
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * resultCode.
     * 
     * @param resultCode
     *            the resultCode to set
     * @since JDK 1.8
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * resultMessage.
     * 
     * @return the resultMessage
     * @since JDK 1.8
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * resultMessage.
     * 
     * @param resultMessage
     *            the resultMessage to set
     * @since JDK 1.8
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * httpStatus.
     * 
     * @return the httpStatus
     * @since JDK 1.8
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * httpStatus.
     * 
     * @param httpStatus
     *            the httpStatus to set
     * @since JDK 1.8
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ResponseCode [resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", httpStatus="
                + httpStatus + "]";
    }
}
