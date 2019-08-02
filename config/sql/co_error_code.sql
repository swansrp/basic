CREATE TABLE `co_error_code`
(
  `err_code`         VARCHAR(50)  NOT NULL COMMENT '错误码',
  `message_outer`    VARCHAR(200) NOT NULL COMMENT '对外信息',
  `message_inner`    VARCHAR(200) NULL     DEFAULT NULL COMMENT '对内信息',
  `author`           VARCHAR(100) NOT NULL COMMENT '作者',
  `create_time`      DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  PRIMARY KEY (`err_code`)
)
  COMMENT ='错误信息表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
