CREATE TABLE `ac_permit`
(
  `id`               INT(10) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_permit_id` INT(10) UNSIGNED    NULL     DEFAULT NULL COMMENT '父权限ID',
  `permit_type`      VARCHAR(20)         NULL     DEFAULT 'MENU' COMMENT '权限类型 MENU|BUTTON',
  `name`             VARCHAR(50)         NOT NULL COMMENT '权限名称',
  `key`              VARCHAR(50)         NOT NULL COMMENT '权限KEY 下划线式驼峰命名',
  `client_type`      VARCHAR(10)         NOT NULL COMMENT '客户端类型',
  `url`              VARCHAR(100)        NOT NULL COMMENT 'URL',
  `show_name`        VARCHAR(50)         NOT NULL COMMENT '显示名称',
  `show_order`       INT(3)              NOT NULL COMMENT '显示顺序',
  `status`           VARCHAR(1)          NOT NULL COMMENT '状态 0 禁用 1 可用',
  `create_time`      DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `icon`             VARCHAR(20)         NULL     DEFAULT NULL COMMENT '图标',
  `valid`            TINYINT(4) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sub_system_url` (`client_type`, `url`),
  UNIQUE INDEX `key` (`key`)
)
  COMMENT ='权限表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
