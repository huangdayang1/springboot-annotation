CREATE TABLE IF NOT EXISTS `user` (
  `id`          INT UNSIGNED AUTO_INCREMENT,
  `username`    VARCHAR(20)             NOT NULL,
  `address`     VARCHAR(20)             NOT NULL,
  `password`    VARCHAR(16)             NOT NULL,
  `email`       VARCHAR(50),
  `status`      smallint(6) default '0' not null
  comment '0：正常; 1：删除',
  `date_birth`  TIMESTAMP
  comment '出生日期',
  `date_joined` TIMESTAMP    DEFAULT now()
  comment '注册日期',
  `date_update` TIMESTAMP    DEFAULT now()
  ON UPDATE CURRENT_TIMESTAMP
  comment '更新日期',
  constraint index_user_username
  unique (username),
  constraint index_user_email
  unique (email),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
