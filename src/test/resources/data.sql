INSERT INTO user (email, password, age, name ,marry,disorder, gender) VALUES('test@email.com','e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00',13,'test','do',false ,'women');
INSERT INTO user (email, password, age, name ,marry,disorder, gender) VALUES('test3@email.com','e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00',13,'test2','do',false ,'women');

INSERT INTO post (id,category,content,created_at,title,user) VALUES (1,'test','test 좀 합니다.','2021-03-19 02:26:32','head','test@email.com');
INSERT INTO post (id, category,content,created_at,title,user) VALUES (2,'test3','test 좀 합니다.','2021-03-19 02:26:33','head3','test3@email.com');

INSERT INTO open_api_post(serv_id,inq_num,jur_mnof_nm,jur_org_nm,serv_dgst,serv_dtl_link,serv_nm,svcfrst_reg_ts) VALUES ('WII00000001','3995','국가보훈처','선양정책과','나야','나야','나야','2020-07-22');
INSERT INTO open_api_post(serv_id,inq_num,jur_mnof_nm,jur_org_nm,serv_dgst,serv_dtl_link,serv_nm,svcfrst_reg_ts) VALUES ('WII00000002','3995','국가보훈처','선양정책과','나야','나야','나야','2020-07-22');

INSERT INTO open_api_category(id,category_name,post_id)VALUES(1,'교육','WII00000001');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(2,'영유아','WII00000002');
INSERT INTO open_api_category(id,category_name,post_id)VALUES(3,'영유아','WII00000001');

INSERT INTO comments(id,sequence,comments,post_id,user,depth,parents) values(1,1,'2',1,'test@email.com',0,0);
