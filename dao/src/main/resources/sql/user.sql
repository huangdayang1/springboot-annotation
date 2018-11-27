CREATE TABLE IF NOT EXISTS `user` (
  `id`          INT UNSIGNED AUTO_INCREMENT,
  `username`    VARCHAR(20) NOT NULL,
  `age`         INT         NOT NULL,
  `address`     VARCHAR(20) NOT NULL,
  `password`    VARCHAR(16) NOT NULL,
  `email`       VARCHAR(50),
  `date_joined` TIMESTAMP    DEFAULT now(),
  `date_update` TIMESTAMP    DEFAULT now()
  ON UPDATE CURRENT_TIMESTAMP,
  constraint index_user_username
  unique (username),
  constraint index_user_email
  unique (email),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
