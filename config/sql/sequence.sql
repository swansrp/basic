CREATE TABLE `sequence`
(
  `seq_name`         VARCHAR(128) NOT NULL COMMENT '序列名称',
  `value`            INT(11)      NOT NULL COMMENT '目前序列值',
  `prefix`           VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '序列前缀',
  `suffix`           VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '序列后缀',
  `min_value`        INT(11)      NOT NULL COMMENT '最小值',
  `max_value`        INT(11)      NOT NULL COMMENT '最大值',
  `step`             INT(11)      NOT NULL DEFAULT '1' COMMENT '每次取值的数量',
  `create_time`      DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  PRIMARY KEY (`seq_name`)
)
  COMMENT ='队列表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
