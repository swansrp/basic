/**
 * @Title: MySlf4jLogInfo.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.utils.log
 * @author Sharp
 * @date 2019-01-23 11:16:31
 */
package com.srct.service.utils.log;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
@AllArgsConstructor
public class MySlf4jLogInfo {
    private String methodName;

    private String paramNames;

    private Long startTime;

    private Long endTime;
}
