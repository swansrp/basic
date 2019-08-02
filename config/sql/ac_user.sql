CREATE TABLE `ac_user`
(
  `id`                        INT(10) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT 'id',
  `guid`                      VARCHAR(32)         NOT NULL COMMENT '系统唯一标识',
  `customer_no`               VARCHAR(10)         NOT NULL COMMENT '用户流水号',
  `user_id`                   VARCHAR(50)         NOT NULL COMMENT '用户登录名',
  `password`                  VARCHAR(128)        NULL     DEFAULT NULL COMMENT '密码',
  `name`                      VARCHAR(60)         NULL     DEFAULT NULL COMMENT '用户名称',
  `phone`                     VARCHAR(20)         NULL     DEFAULT NULL COMMENT '电话号码',
  `email`                     VARCHAR(50)         NULL     DEFAULT NULL COMMENT '邮箱号码',
  `wechat_id`                 VARCHAR(50)         NULL     DEFAULT NULL COMMENT '微信openId',
  `id_card_num`               VARCHAR(20)         NULL     DEFAULT NULL COMMENT '身份证号码',
  `app_key`                   VARCHAR(30)         NULL     DEFAULT NULL COMMENT 'openAPI key',
  `app_secret`                VARCHAR(100)        NULL     DEFAULT NULL COMMENT 'openAPI secret',
  `comment`                   VARCHAR(50)         NULL     DEFAULT NULL COMMENT '备注',
  `password_error_times`      INT(2)              NOT NULL DEFAULT '0' COMMENT '连续输错密码次数，输入正确密码后归零',
  `password_error_last_time`  DATETIME(6)         NULL     DEFAULT NULL COMMENT '上次输错密码时间',
  `password_update_last_time` DATETIME(6)         NULL     DEFAULT CURRENT_TIMESTAMP(6) COMMENT '上次密码修改时间',
  `status`                    VARCHAR(1)          NOT NULL COMMENT '状态 0 禁用 1 可用',
  `company`                   INT(10) UNSIGNED    NULL     DEFAULT NULL COMMENT '所属单位',
  `create_time`               DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time`          DATETIME(6)         NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `valid`                     TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_id` (`user_id`),
  UNIQUE INDEX `phone` (`phone`),
  UNIQUE INDEX `email` (`email`),
  UNIQUE INDEX `wechat_id` (`wechat_id`),
  UNIQUE INDEX `id_card_num` (`id_card_num`)
)
  COMMENT ='操作员表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
;
