package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.srct.service.dao.entity.DictionaryItem")
@Data
@Table(name = "co_dictionary_item")
public class DictionaryItem {
    /**
     * 所属字典ID
     */
    @Id
    @Column(name = "dictionary_id")
    @ApiModelProperty(value="所属字典ID")
    private String dictionaryId;

    /**
     * 条目值
     */
    @Id
    @Column(name = "item_id")
    @ApiModelProperty(value="条目值")
    private String itemId;

    /**
     * 条目名
     */
    @Column(name = "item_name")
    @ApiModelProperty(value="条目名")
    private String itemName;

    /**
     * 条目顺序
     */
    @Column(name = "item_order")
    @ApiModelProperty(value="条目顺序")
    private Integer itemOrder;

    /**
     * 条目备注
     */
    @Column(name = "remark")
    @ApiModelProperty(value="条目备注")
    private String remark;
}
