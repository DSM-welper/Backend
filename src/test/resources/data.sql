INSERT INTO user (email, password, age, name ,marry,disorder, gender) VALUES('test@email.com','e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00',13,'test','do',false ,'women');
INSERT INTO user (email, password, age, name ,marry,disorder, gender) VALUES('test3@email.com','e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00',13,'test2','do',false ,'women');

INSERT INTO post (id,category,content,created_at,title,user) VALUES (1,'test','test 좀 합니다.','2021-03-19 02:26:32','head','test@email.com');
INSERT INTO post (id, category,content,created_at,title,user) VALUES (2,'test3','test 좀 합니다.','2021-03-19 02:26:33','head3','test3@email.com');

INSERT INTO open_api_post(serv_id,inq_num,jur_mnof_nm,jur_org_nm,serv_dgst,serv_dtl_link,serv_nm,svcfrst_reg_ts) VALUES ('WII00000001','3995','국가보훈처','선양정책과','나야','나야','나야','2020-07-22');
INSERT INTO open_api_post(serv_id,inq_num,jur_mnof_nm,jur_org_nm,serv_dgst,serv_dtl_link,serv_nm,svcfrst_reg_ts) VALUES ('WII00000002','3995','국가보훈처','선양정책과','나야','나야','나야','2020-07-22');
INSERT INTO open_api_post(serv_id,inq_num,jur_mnof_nm,jur_org_nm,serv_dgst,serv_dtl_link,serv_nm,svcfrst_reg_ts) VALUES ('WII00000003','3995','국가보훈처','선양정책과','나야','나야','나야','2020-07-22');

INSERT INTO open_api_category(id,category_name,post_id)VALUES(1,'교육','WII00000001');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(2,'교육','WII00000002');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(3,'교육','WII00000003');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(4,'영유아','WII00000002');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(5,'영유아','WII00000001');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(6,'영유아','WII00000003');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(7,'여성','WII00000003');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(8,'여성','WII00000002');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(9,'여성','WII00000001');

INSERT INTO comments(id,sequence,comments,post_id,user,depth,parents) values(1,1,'2',1,'test@email.com',0,0);

INSERT INTO email_certify(email,auth_code,certified) values('test@email.com','a9a406153c49650fc7596654ba77f4a6254e214bcd65a45f4717727b3decd264fa4f3d244b735ab1e55830cff078e58801117a127f9f1d62cf1589f9103e9339',true);
INSERT INTO email_certify(email,auth_code,certified) values('test2@email.com','a9a406153c49650fc7596654ba77f4a6254e214bcd65a45f4717727b3decd264fa4f3d244b735ab1e55830cff078e58801117a127f9f1d62cf1589f9103e9339',false );
