/**  
 * Project Name:SpringBootCommon  
 * File Name:CommonResponse.java  
 * Package Name:com.srct.service.config.response  
 * Date:Apr 26, 2018 7:46:52 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.config.response;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.srct.service.exception.AccessTokenExpiredException;
import com.srct.service.exception.AccountOrPasswordIncorrectException;
import com.srct.service.exception.ServiceException;
import com.srct.service.exception.UserNotLoginException;

/**
 * ClassName:CommonResponse <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 26, 2018 7:46:52 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * Basic Exception Handler
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String msg = sw.toString();
        CommonResponse res = new CommonResponse(CommonResponseConstant.SERVER_ERROR, msg);
        return res.getEntity();
    }

    /**
     * Portal 登陆账号或密码错误 异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AccountOrPasswordIncorrectException.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(AccountOrPasswordIncorrectException ex) {
        CommonResponse res = new CommonResponse(CommonResponseConstant.ACCOUNT_OR_PASSWORD_INCORRECT_ERROR, null);
        return res.getEntity();
    }

    /**
     * AccessToken过期 异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AccessTokenExpiredException.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(AccessTokenExpiredException ex) {
        CommonResponse res = new CommonResponse(CommonResponseConstant.ACCESS_TOKEN_EXPIRED_OR_INVALID_ERROR,
                ex.getMessage());
        return res.getEntity();
    }

    /**
     * User Not Login 异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = UserNotLoginException.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(UserNotLoginException ex) {
        CommonResponse res = new CommonResponse(CommonResponseConstant.USER_NOT_LOGIN_ERROR, ex.getMessage());
        return res.getEntity();
    }

    /**
     * 必传参数异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("Invalid Request:\n");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessageBuilder.append(fieldError.getDefaultMessage() + "\n");
        }
        CommonResponse res = new CommonResponse(CommonResponseConstant.SERVER_ERROR, errorMessageBuilder.toString());
        return res.getEntity();
    }

    /**
     * 全局异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(ServiceException ex) {
        CommonResponse res = new CommonResponse(CommonResponseConstant.SERVER_ERROR, ex.getMessage());
        return res.getEntity();
    }

    /**
     * return response with data,if data is null,return no data message,or
     * return data
     *
     * @param data
     * @return
     */
    @ResponseBody
    @Async
    public static ResponseEntity<CommonResponse.Resp> generateResponse(Object data) {
        CommonResponse res = new CommonResponse(CommonResponseConstant.SUCCESS, data);
        return res.getEntity();
    }
}
