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
