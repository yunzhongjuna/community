## 学习社区

##资料文档

[bootstrap](https://www.bootcss.com/)
[thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#variables)

##工具

[Git](https://git-scm.com/download) 

##脚本
```sql
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
```
```sql
CREATE TABLE question
(
    id int PRIMARY KEY AUTO_INCREMENT,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modified bigint,
    creator int,
    comment_count int DEFAULT 0,
    view_count int DEFAULT 0,
    like_count int DEFAULT 0,
    tag varchar(256)
);
```