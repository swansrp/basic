/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${responsePackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${responsePackage};

import org.springframework.http.HttpStatus;

import ${BASIC_PACKAGE}.config.response.CommonResponseCode;
import ${BASIC_PACKAGE}.config.response.CommonResponseConstant;

public class LockScreenResponseConstant extends CommonResponseConstant {
	public static final String TAG = "${projectNameFS}";
	public static final CommonResponseCode SUCCESS = new CommonResponseCode(TAG+"0000", "Success", HttpStatus.OK);
	public static final CommonResponseCode SERVER_ERROR = new CommonResponseCode(TAG+"0001", "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	public static final CommonResponseCode DB_ERROR = new CommonResponseCode(TAG+"0002", "DB Error", HttpStatus.SERVICE_UNAVAILABLE);
	public static final CommonResponseCode PERMISSION_DENIED_ERROR = new CommonResponseCode(TAG+"0003", "Access denied", HttpStatus.FORBIDDEN);
}