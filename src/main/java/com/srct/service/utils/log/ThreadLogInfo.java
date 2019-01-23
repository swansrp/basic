/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.utils.log
 * @author: ruopeng.sha
 * @date: 2018-11-10 16:12
 */
package com.srct.service.utils.log;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: ThreadLogInfo
 * @Description: TODO
 */
@Data
@AllArgsConstructor
public class ThreadLogInfo {

    private String url;

    private String ipAddress;

    private String header;

    private String methodName;

    private String paramNames;

    private Long startTime;

    private Long endTime;
}
