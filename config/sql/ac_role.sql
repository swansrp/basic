CREATE TABLE `ac_role`
(
  `id`               INT(10) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `parent_role_id`   INT(10) UNSIGNED    NULL     DEFAULT NULL COMMENT '父角色ID',
  `name`             VARCHAR(50)         NOT NULL COMMENT '角色名称',
  `create_time`      DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `valid`            TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`)
)
  COMMENT ='角色表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
