INSERT INTO `employee`(id,first_name,last_name,nick_name,email,telephone,address,city,country,post_code,hired_date,created_date,modified_date,version)
 VALUES(1,'Administrator','Admin','admin','admin@dummy.com','123456789','123/4','Bangkok','Thailand',10120,now(),now(),now(),0);
INSERT INTO `user`(id,username,password,token,token_created_date,created_date,modified_date,version)
 VALUES(1,'admin@dummy.com','$2a$10$VAvx.kXVe.z9HC8rN3hw1uQuVxokaJkHUyBhaDcFyWpPcN0Yj7FeW',null,null,now(),now(),0);