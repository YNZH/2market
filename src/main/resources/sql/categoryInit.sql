DROP TABLE IF EXISTS tb_category;
CREATE TABLE tb_category (
  pk_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  src VARCHAR(129),
  name  VARCHAR(64)
);

INSERT INTO tb_category (src,name) VALUES ("../img/index.png","最新发布");
INSERT INTO tb_category (src,name) VALUES ("../img/digital.png","闲置数码");
INSERT INTO tb_category (src,name) VALUES ("../img/ride.png","校园代步");
INSERT INTO tb_category (src,name) VALUES ("../img/commodity.png","电器日用");
INSERT INTO tb_category (src,name) VALUES ("../img/book.png","图书教材");
INSERT INTO tb_category (src,name) VALUES ("../img/makeup.png","美妆日用");
INSERT INTO tb_category (src,name) VALUES ("../img/sport.png","运动棋牌");
INSERT INTO tb_category (src,name) VALUES ("../img/smallthing.png","票券小物");
