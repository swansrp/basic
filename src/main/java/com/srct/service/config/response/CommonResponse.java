/**  
 * Project Name:SpringBootCommon  
 * File Name:CommonResponse.java  
 * Package Name:com.srct.service.config.response  
 * Date:Apr 26, 2018 7:31:49 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/  
  
package com.srct.service.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**  
 * ClassName:CommonResponse <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     Apr 26, 2018 7:31:49 PM <br/>  
 * @author   ruopeng.sha  
 * @version    
 * @since    JDK 1.8  
 * @see        
 */
public class CommonResponse{

	private Resp res;
	private HttpStatus status;
    public class Resp { 
    	public String code;
    	public String message;
    	public Object data;
    }
    
    public CommonResponse(CommonResponseCode code, Object obj) { 
    	res = new Resp();
    	res.code = code.getResultCode();
    	res.message = code.getResultMessage();
    	res.data = obj;
    	status = code.getHttpStatus();
    }
 
    public ResponseEntity<Resp> getEntity() {
    	return new ResponseEntity<Resp>(res,status);
    }
    
    public ResponseEntity<Resp> getEntity(MultiValueMap<String, String> headers) {
    	return new ResponseEntity<Resp>(res,headers,status);
    }

	/**  
	 * res.  
	 *  
	 * @return  the res  
	 * @since   JDK 1.8  
	 */
	public Resp getRes() {
		return res;
	}

	/**  
	 * res.  
	 *  
	 * @param   res    the res to set  
	 * @since   JDK 1.8  
	 */
	public void setRes(Resp res) {
		this.res = res;
	}

	/**  
	 * status.  
	 *  
	 * @return  the status  
	 * @since   JDK 1.8  
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**  
	 * status.  
	 *  
	 * @param   status    the status to set  
	 * @since   JDK 1.8  
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/** 
	 * @see java.lang.Object#toString()  
	 */
	@Override
	public String toString() {
		return "Response [res=" + res + ", status=" + status + "]";
	}

}
  
