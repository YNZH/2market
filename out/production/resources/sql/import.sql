DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
  pk_id         BIGINT PRIMARY KEY AUTO_INCREMENT,
  nickname      VARCHAR(64),
  password      VARCHAR(132),
  email         VARCHAR(64),
  phone         VARCHAR(15),
  gender        ENUM ('MALE', 'FEMALE'),
  school        VARCHAR(32),
  campus        VARCHAR(32),
  number        VARCHAR(16),
  head_img      VARCHAR(132),
  is_blocked    TINYINT,
  time_create   DATETIME,
  time_modified DATETIME
);

DROP TABLE IF EXISTS tb_goods;
CREATE TABLE tb_goods (
  pk_id         BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id       BIGINT,
  name          VARCHAR(132),
  src           VARCHAR(132),
  price         DECIMAL(18, 3),
  description   VARCHAR(512),
  category      VARCHAR(64),
  location      VARCHAR(32),
  campus        VARCHAR(32),
  time_create   DATETIME,
  time_modified DATETIME
);

DROP TABLE IF EXISTS tb_comment;
CREATE TABLE tb_comment (
  pk_id         BIGINT PRIMARY KEY AUTO_INCREMENT,
  goods_id      BIGINT,
  user_id       BIGINT,
  parent_id     BIGINT,
  content       VARCHAR(512),
  pics          VARCHAR(256),
  time_create   DATETIME,
  time_modified DATETIME
);

DROP TABLE IF EXISTS tb_message;
CREATE TABLE tb_message(
  pk_id BIGINT PRIMARY KEY  AUTO_INCREMENT,
  formId BIGINT,
  toId BIGINT,
  fromName VARCHAR(64),
  headerImg VARCHAR(132),
  content TEXT,
  offline TINYINT,
  time_create DATETIME
);

DROP TABLE IF EXISTS tb_login_record;
CREATE TABLE tb_login_record(
  pk_id BIGINT PRIMARY KEY  AUTO_INCREMENT,
  user_id BIGINT,
  os_name VARCHAR(256),
  device VARCHAR(256),
  browser_type VARCHAR(256),
  ip_address VARCHAR(50),
  time_create DATETIME
)
