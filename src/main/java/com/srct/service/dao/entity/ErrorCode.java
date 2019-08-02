package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value = "com.srct.service.dao.entity.ErrorCode")
@Data
@Table(name = "co_error_code")
public class ErrorCode {
    /**
     * 错误码
     */
    @Id
    @Column(name = "err_code")
    @ApiModelProperty(value = "错误码")
    private String errCode;

    /**
     * 对外信息
     */
    @Column(name = "message_outer")
    @ApiModelProperty(value = "对外信息")
    private String messageOuter;

    /**
     * 对内信息
     */
    @Column(name = "message_inner")
    @ApiModelProperty(value = "对内信息")
    private String messageInner;

    /**
     * 作者
     */
    @Column(name = "author")
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value = "修改时间")
    private Date lastUpdateTime;
}
