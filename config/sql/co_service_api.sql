CREATE TABLE `co_service_api`
(
  `api_id`           VARCHAR(20)  NOT NULL COMMENT '接口编号',
  `url`              VARCHAR(50)  NOT NULL COMMENT '接口URL',
  `description`      VARCHAR(100) NOT NULL COMMENT '接口描述',
  `method`           VARCHAR(10)  NOT NULL DEFAULT 'GET' COMMENT 'GET/POST/DELETE/PULL',
  `api_version`      VARCHAR(5)   NOT NULL DEFAULT 'V1.0' COMMENT '版本号 默认1.0',
  `status`           VARCHAR(1)   NOT NULL DEFAULT '1' COMMENT '状态 1可用 0禁用',
  `remark`           VARCHAR(100) NULL     DEFAULT NULL COMMENT '备注',
  `author`           VARCHAR(100) NOT NULL COMMENT '作者',
  `need_login`       VARCHAR(1)   NOT NULL DEFAULT '0' COMMENT '校验登录',
  `need_token`       VARCHAR(1)   NOT NULL DEFAULT '0' COMMENT '校验token',
  `need_req_token`   VARCHAR(1)   NOT NULL DEFAULT '0' COMMENT '校验reqToken',
  `need_permit`      VARCHAR(1)   NOT NULL DEFAULT '0' COMMENT '校验权限',
  `create_time`      DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_update_time` DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `valid`            TINYINT(4)   NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`api_id`)
)
  COMMENT ='接口定义表'
  COLLATE = 'utf8_general_ci'
  ENGINE = InnoDB
;
