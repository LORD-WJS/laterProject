
--用户表
create table user(
id varchar(50) primary key ,
username varchar(50),--法名
name varchar(50),    --姓名
password varchar(50),
salt varchar(50),--盐
phone varchar(50),
face varchar(200), --头像
sex varchar(10),
location varchar(100),--所在城市名
signature varchar(100),--签名
createDate date,
lastLoginTime timestamp,
status varchar(50) --正常|冻结
);

--地址
-- create table address(
-- id varchar(50) primary key ,
-- name varchar(50),
-- level varchar(10),
-- parentId varchar(50)
-- );

--轮播图
create table banner(
id varchar(50) primary key ,
name varchar(50),
path varchar(400),
href varchar (200),
createDate date,
introduce varchar (200),
status varchar(50)
);


insert into banner values('1','夜景','009e9dfd5266d016d30938279a2bd40735fa3576.jpg',null,now(),'beautiful','正常');
--专辑 吉祥妙音
create table album(
id varchar(50) primary key ,
name varchar(50),
cover varchar(50),
star int(3),
author varchar(50),
announcer varchar(50),
chapterNum int(3),
introduce varchar(1000),
status varchar (50),
createDate date
);

--专辑章节  audio音频
create table chapter(
id varchar(50) primary key ,
name varchar(50),
audioPath varchar(200),
audioSize varchar(50),
time varchar (50),
createDate date,
albumId varchar(50)
);

--上师 法师
create table master(
id varchar(50) primary key ,
name varchar(50),
face varchar(200),
status varchar (50),
nickName varchar (50)
);

--文章
create table article(
id varchar(50) primary key,
name varchar(50),
picpath varchar (200),
content varchar(2000),
createDate date,
status varchar (50),
masterId varchar(50)
);

--文章内图片
-- create table articleImg(
-- id varchar(50) primary key ,
-- picpath varchar(50)
-- );


--功课记录
create table course(
id varchar(50) primary key ,
name varchar(50),
userId varchar (50),
type varchar (50),
createDate date
);

--计数器
create table counter(
id varchar (50) primary key ,
name varchar (50),
count int(10),
createDate date,
userId varchar (50),
courseId varchar(50)
);

--管理员表
create table admin(
id varchar (50) primary key ,
username varchar (50),
password varchar (50)
);

insert into admin values('1','admin','admin');

--关注上师表
create table attention(
id varchar (50) primary key ,
masterId varchar (50),
userId varchar (50)
);

--角色表
create table role(
id varchar (50) primary key ,
name varchar (50)
);
insert into role values('1','user');
insert into role values('2','vip');
insert into role values('3','admin');
insert into role values('4','superAdmin');

--用户角色中间表(用户即为使用者,含前台用户 后台管理员)
create table userRole(
id varchar (50) primary key ,
userId varchar (50),
roleId varchar (50)
);
insert into userRole values('1','1','3');

--权限资源表
create table resource(
id varchar (50) primary key ,
name varchar (100)
);
insert into resource values('1','user:*');


--角色权限关系表
create table roleResource(
id varchar (50) primary key ,
roleId varchar (50),
resourceId varchar (50)
);
insert into roleResource values('1','3',1);


































