CREATE TABLE `co_parameter`
(
  `parameter_id` VARCHAR(50)   NOT NULL COMMENT '参数编号',
  `value`        VARCHAR(2000) NOT NULL COMMENT '参数值',
  `remark`       VARCHAR(500)  NULL DEFAULT NULL COMMENT '注释',
  `module`       VARCHAR(20)   NULL DEFAULT NULL COMMENT '模块名',
  `manage_type`  VARCHAR(1)    NULL DEFAULT NULL COMMENT '维护人员',
  PRIMARY KEY (`parameter_id`)
)
  COMMENT ='参数表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
