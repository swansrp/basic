/**
 * @Title: RestResponse.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.utils.conn
 * @author sharp
 * @date 2019-01-22 18:50:17
 */
package com.srct.service.utils.conn;

import lombok.Data;

/**
 * @author sharp
 *
 */
@Data
public class RestResponse<T> {

    private String code;

    private String message;

    private T data;
}
