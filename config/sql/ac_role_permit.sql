CREATE TABLE `ac_role_permit`
(
  `permit_id` INT(10) UNSIGNED    NOT NULL COMMENT '权限ID',
  `role_id`   INT(10) UNSIGNED    NOT NULL COMMENT '角色ID',
  `valid`     TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`permit_id`, `role_id`)
)
  COMMENT ='角色和权限的关系表，建立关系表示角色拥有该权限'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
