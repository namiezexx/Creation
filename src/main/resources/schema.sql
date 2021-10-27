DROP TABLE IF EXISTS social;

CREATE TABLE social (
    social_seq bigint not null primary key auto_increment,
    user_seq bigint,
    provider varchar(30),
    provider_uid varchar(100),
    access_token varchar(255),
    refresh_token varchar(255),
    nickname varchar(255),
    FOREIGN KEY(user_seq) REFERENCES user(user_seq)
);

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS user;
SET foreign_key_checks = 1;

CREATE TABLE user (
    user_seq bigint not null primary key auto_increment,
    user_id varchar(50) not null unique,
    user_pw varchar(100),
    nickname varchar(50),
    age int,
    email varchar(50),
    phone varchar(20),
    role varchar(100) default 'ROLE_USER'
);
