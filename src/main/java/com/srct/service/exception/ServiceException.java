/**  
 * Project Name:SpringBootCommon  
 * File Name:ServiceException.java  
 * Package Name:com.srct.service.config.response  
 * Date:Apr 26, 2018 7:50:00 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/  
  
package com.srct.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**  
 * ClassName:ServiceException <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     Apr 26, 2018 7:50:00 PM <br/>  
 * @author   ruopeng.sha  
 * @version    
 * @since    JDK 1.8  
 * @see        
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(String msg) {
        super(msg);
    }
    public ServiceException() {
        super();
    }
}
  
