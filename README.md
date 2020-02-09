## 学习社区

##资料文档
[bootstrap]https://www.bootcss.com/

##工具
[Git](https://git-scm.com/download) 

##脚本
~~~sql
create table user
(
  id           int auto_increment
    primary key,
  account_id   varchar(100) null,
  name         varchar(50)  null,
  token        char(36)     null,
  gmt_create   bigint       null,
  gmt_modified bigint       null,
  bio          varchar(256) null
);
~~~