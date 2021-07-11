show databases ;

create database sc_order;

use sc_order;

drop table orders;

create table orders (
                        id integer auto_increment primary key not null ,
                        account_id INTEGER NOT NULL COMMENT '账户id',
                        product_id Integer not null comment '商品id',
                        product_name varchar(100) default null comment '商品名称',
                        num integer default 0 comment '数量',
                        price double default 0.0 comment '价格'
);

select * from orders;

insert into orders (account_id, product_id, product_name, num, price) VALUE (1, 1, '华为手机p40', 2, 4500);