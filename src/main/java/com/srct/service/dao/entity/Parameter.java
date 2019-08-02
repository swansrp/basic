package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value = "com.srct.service.dao.entity.Parameter")
@Data
@Table(name = "co_parameter")
public class Parameter {
    /**
     * 参数编号
     */
    @Id
    @Column(name = "parameter_id")
    @ApiModelProperty(value = "参数编号")
    private String parameterId;

    /**
     * 参数值
     */
    @Column(name = "`value`")
    @ApiModelProperty(value = "参数值")
    private String value;

    /**
     * 注释
     */
    @Column(name = "remark")
    @ApiModelProperty(value = "注释")
    private String remark;

    /**
     * 模块名
     */
    @Column(name = "`module`")
    @ApiModelProperty(value = "模块名")
    private String module;

    /**
     * 维护人员
     */
    @Column(name = "manage_type")
    @ApiModelProperty(value = "维护人员")
    private String manageType;
}
