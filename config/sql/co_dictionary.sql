CREATE TABLE `co_dictionary`
(
  `dictionary_id` VARCHAR(100) NOT NULL COMMENT '字典ID',
  `description`   VARCHAR(100) NULL DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`dictionary_id`)
)
  COMMENT ='系统字典表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
