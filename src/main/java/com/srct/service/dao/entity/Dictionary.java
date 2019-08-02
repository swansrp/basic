package com.srct.service.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.srct.service.dao.entity.Dictionary")
@Data
@Table(name = "co_dictionary")
public class Dictionary {
    /**
     * 字典ID
     */
    @Id
    @Column(name = "dictionary_id")
    @ApiModelProperty(value="字典ID")
    private String dictionaryId;

    /**
     * 说明
     */
    @Column(name = "description")
    @ApiModelProperty(value="说明")
    private String description;
}
