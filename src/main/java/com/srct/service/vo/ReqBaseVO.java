/**
 * Title: ReqBaseVO.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-15 10:09
 * @description Project Name: Tanya
 * @Package: com.srct.service.vo
 */
package com.srct.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ReqBaseVO {
    @ApiModelProperty("起始时间{yyyy-MM-dd HH:mm:ss}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date queryStartAt;
    @ApiModelProperty("结束时间{yyyy-MM-dd HH:mm:ss}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date queryEndAt;
}
