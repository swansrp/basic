/**
 * Title: ErrCodeSys.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 21:54
 * @description Project Name: Grote
 * @Package: com.srct.service.validate
 */
package com.srct.service.constant;

public enum ErrCodeSys implements ErrCode {
    // 当前配置不支持该%s
    SYS_CONFIG_NOT_EXIST,
    // %s不能为空
    PA_PARAM_NULL,
    // %s格式错误
    PA_PARAM_FORMAT,
    // 该%s数据已存在
    PA_DATA_HAS_EXIST,
    // 该%s数据不存在
    PA_DATA_NOT_EXIST,
    // 该%s数据不相同
    PA_DATA_DIFF,
    // 系统错误
    SYS_ERR,
    // %s
    SYS_ERR_MSG,
    // 登录超时,%s不一致
    SYS_SESSION_NOT_SAME,
    // 登录超时
    SYS_SESSION_TIME_OUT,
    // 权限错误:%s
    SYS_PERMIT_ERROR
}
