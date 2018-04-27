DROP TABLE IF EXISTS tb_school;
CREATE TABLE tb_school (
  pk_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(64),
  campus VARCHAR(64)
);

INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","余家头校区");
INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","升升公寓");
INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","东院");
INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","西院");
INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","鉴湖");
INSERT INTO tb_school(name, campus) VALUES ("武汉理工大学","南湖");
INSERT INTO tb_school(name, campus) VALUES ("其他高校","其他校区");
















