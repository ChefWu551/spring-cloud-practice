show databases ;

use sc_payment;

create table user_account(
    id integer auto_increment primary key not null ,
    account_name varchar(30) default null comment '账户名称',
    account_money decimal(20, 2) default 0.0 comment '账户金额'
);

select * from user_account;

insert into user_account (account_name, account_money) VALUE ('yuefeng', 5000);

select * from user_account;