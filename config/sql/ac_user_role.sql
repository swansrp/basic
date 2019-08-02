CREATE TABLE `ac_user_role`
(
  `user_id` INT(10) UNSIGNED    NOT NULL COMMENT '用户ID',
  `role_id` INT(10) UNSIGNED    NOT NULL COMMENT '角色ID',
  `valid`   TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`user_id`, `role_id`)
)
  COMMENT ='操作员与角色映射表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
