package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.srct.service.dao.entity.ServicePermit")
@Data
@Table(name = "co_service_permit")
public class ServicePermit {
    /**
     * 接口id
     */
    @Id
    @Column(name = "api_id")
    @ApiModelProperty(value="接口id")
    private String apiId;

    /**
     * 权限id
     */
    @Id
    @Column(name = "permit_id")
    @ApiModelProperty(value="权限id")
    private Integer permitId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;
}