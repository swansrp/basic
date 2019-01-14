/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${responsePackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${responsePackage};

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ${BASIC_PACKAGE}.config.response.CommonExceptionHandler;
import ${BASIC_PACKAGE}.config.response.CommonResponse;
import ${BASIC_PACKAGE}.utils.log.Log;

@RestControllerAdvice
public class ${projectName}ExceptionHandler extends CommonExceptionHandler {

    /**
     * 全局异常捕捉处理
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
        Log.i(msg);
        CommonResponse res = new CommonResponse(${projectName}ResponseConstant.SERVER_ERROR, ex.getMessage());
        return res.getEntity();
    }
    
    /**
     * return response with data,if data is null,return no data message,or return
     * data
     *
     * @param data
     * @return
     */
    @ResponseBody
    public static ResponseEntity<CommonResponse.Resp> generateResponse(Object data) {
        CommonResponse res = new CommonResponse(${projectName}ResponseConstant.SUCCESS, data);
        return res.getEntity();
    }
}
