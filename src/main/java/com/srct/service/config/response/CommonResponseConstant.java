/**
 * Project Name:SpringBootCommon File Name:CommonConstant.java Package Name:com.srct.service.utils Date:Apr 26, 2018
 * 7:52:35 PM Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 * 
 */
package com.srct.service.config.response;

import org.springframework.http.HttpStatus;

/**
 * ClassName:CommonConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 26, 2018 7:52:35 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class CommonResponseConstant {

    public static final String TAG = "SRP";

    public static final CommonResponseCode SUCCESS = new CommonResponseCode(TAG + "0000", "Success", HttpStatus.OK);

    public static final CommonResponseCode SERVER_ERROR =
        new CommonResponseCode(TAG + "0001", "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    public static final CommonResponseCode DB_ERROR =
        new CommonResponseCode(TAG + "0002", "DB Error", HttpStatus.SERVICE_UNAVAILABLE);

    public static final CommonResponseCode PERMISSION_DENIED_ERROR =
        new CommonResponseCode(TAG + "0003", "Access denied", HttpStatus.FORBIDDEN);

    public static final CommonResponseCode ACCESS_TOKEN_EXPIRED_OR_INVALID_ERROR =
        new CommonResponseCode(TAG + "0005", "Access Token Expired Or Invalid Error", HttpStatus.OK);

    public static final CommonResponseCode ACCOUNT_OR_PASSWORD_INCORRECT_ERROR =
        new CommonResponseCode(TAG + "0006", "Account Or Passwork Incorrect", HttpStatus.OK);

    public static final CommonResponseCode USER_NOT_LOGIN_ERROR =
        new CommonResponseCode(TAG + "0004", "User Not Login Error", HttpStatus.OK);

    public static final CommonResponseCode REDIRECT = new CommonResponseCode(TAG + "9999", "Redirect", HttpStatus.OK);
}
