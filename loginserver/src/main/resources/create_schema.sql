CREATE TABLE `accounts` (
  `login`        VARCHAR(16) NULL,
  `password`     VARCHAR(64) NULL,
  `lastLogin`    DATETIME    NULL,
  `accessLevel`  TINYINT     NULL,
  `lastIp`       VARCHAR(15) NULL,
  `lastServerId` INT         NULL DEFAULT 0,
  PRIMARY KEY (`login`)
);

CREATE TABLE `accounts_log` (
  `id`        INT         NULL AUTO_INCREMENT,
  `login`     VARCHAR(16) NULL,
  `operation` TINYINT     NULL,
  `msgId`     SMALLINT    NULL,
  `time`      DATETIME    NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `accounts_settings` (
  `id`    INT         NULL AUTO_INCREMENT,
  `login` VARCHAR(16) NULL,
  `key`   VARCHAR(64) NULL,
  `value` VARCHAR(64) NULL,
  PRIMARY KEY (`id`, `key`, `login`)
);

ALTER TABLE `accounts_log` ADD FOREIGN KEY (`login`) REFERENCES `accounts` (`login`);
ALTER TABLE `accounts_settings` ADD FOREIGN KEY (`login`) REFERENCES `accounts` (`login`);

