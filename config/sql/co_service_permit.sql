CREATE TABLE `co_service_permit`
(
  `api_id`           VARCHAR(20)      NOT NULL COMMENT '接口id',
  `permit_id`        INT(10) UNSIGNED NOT NULL COMMENT '权限id',
  `create_time`      DATETIME(6)      NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)      NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  PRIMARY KEY (`api_id`, `permit_id`)
)
  COMMENT ='接口权限表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
