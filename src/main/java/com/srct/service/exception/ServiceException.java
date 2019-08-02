/**
 * Project Name:SpringBootCommon File Name:ServiceException.java Package Name:com.srct.service.config.response Date:Apr
 * 26, 2018 7:50:00 PM Copyright (c) 2018, ruopeng.sha All Rights Reserved.
 */
package com.srct.service.exception;

import com.srct.service.constant.ErrCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 * ClassName:ServiceException <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 26, 2018 7:50:00 PM <br/>
 *
 * @author ruopeng.sha
 * @see
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.OK)
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private boolean errorCodeFormat;
    private String code;
    private String[] parameterArr;

    public ServiceException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(ErrCode code, String... parameterArr) {
        super(getText(code.toString(), parameterArr));
        errorCodeFormat = true;
        this.code = code.toString();
        this.parameterArr = parameterArr;
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException() {
        super();
    }

    private static String getText(String codeStr, String... parameterArr) {
        return codeStr + " = " + Arrays.toString(parameterArr);
    }
}
