CREATE TABLE `changelog`
(
  `change_number` VARCHAR(22)  NOT NULL COMMENT '修改编号',
  `complete_dt`   DATETIME(6)  NOT NULL COMMENT '修改时间',
  `applied_by`    VARCHAR(100) NOT NULL COMMENT '修改用户',
  `description`   VARCHAR(500) NOT NULL COMMENT '修改文件',
  PRIMARY KEY (`change_number`)
)
  COMMENT ='数据库变更记录表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
