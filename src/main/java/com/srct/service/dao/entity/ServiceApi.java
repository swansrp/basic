package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value = "com.srct.service.dao.entity.ServiceApi")
@Data
@Table(name = "co_service_api")
public class ServiceApi {
    /**
     * 接口编号
     */
    @Id
    @Column(name = "api_id")
    @ApiModelProperty(value = "接口编号")
    private String apiId;

    /**
     * 接口URL
     */
    @Column(name = "url")
    @ApiModelProperty(value = "接口URL")
    private String url;

    /**
     * 接口描述
     */
    @Column(name = "description")
    @ApiModelProperty(value = "接口描述")
    private String description;

    /**
     * GET/POST/DELETE/PULL
     */
    @Column(name = "`method`")
    @ApiModelProperty(value = "GET/POST/DELETE/PULL")
    private String method;

    /**
     * 版本号 默认1.0
     */
    @Column(name = "api_version")
    @ApiModelProperty(value = "版本号 默认1.0")
    private String apiVersion;

    /**
     * 状态 1可用 0禁用
     */
    @Column(name = "`status`")
    @ApiModelProperty(value = "状态 1可用 0禁用")
    private String status;

    /**
     * 备注
     */
    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 作者
     */
    @Column(name = "author")
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 校验登录
     */
    @Column(name = "need_login")
    @ApiModelProperty(value = "校验登录")
    private String needLogin;

    /**
     * 校验token
     */
    @Column(name = "need_token")
    @ApiModelProperty(value = "校验token")
    private String needToken;

    /**
     * 校验reqToken
     */
    @Column(name = "need_req_token")
    @ApiModelProperty(value = "校验reqToken")
    private String needReqToken;

    /**
     * 校验权限
     */
    @Column(name = "need_permit")
    @ApiModelProperty(value = "校验权限")
    private String needPermit;

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

    /**
     * 有效性
     */
    @Column(name = "`valid`")
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}