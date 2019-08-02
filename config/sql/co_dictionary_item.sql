CREATE TABLE `co_dictionary_item`
(
  `dictionary_id` VARCHAR(100) NOT NULL COMMENT '所属字典ID',
  `item_id`       VARCHAR(100) NOT NULL COMMENT '条目值',
  `item_name`     VARCHAR(50)  NOT NULL COMMENT '条目名',
  `item_order`    INT(3)       NOT NULL COMMENT '条目顺序',
  `remark`        VARCHAR(500) NULL DEFAULT NULL COMMENT '条目备注',
  PRIMARY KEY (`dictionary_id`, `item_id`)
)
  COMMENT ='字典条目表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
