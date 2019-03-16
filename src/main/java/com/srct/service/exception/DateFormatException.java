/**
 * Title: DateFormatException.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.exception
 * @author sharuopeng
 * @date 2019-03-13 09:33:34
 */
package com.srct.service.exception;

/**
 * @author sharuopeng
 *
 */
public class DateFormatException extends ServiceException {

    private static final long serialVersionUID = -4107172839941176337L;
    private static final String DEFAULT_MESSAGE = "日期格式错误：";

    public DateFormatException(String dateStr, Throwable e) {
        super(DEFAULT_MESSAGE + dateStr);
    }
}
