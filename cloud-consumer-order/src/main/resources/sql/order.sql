show databases ;

create database sc_order;

use sc_order;

create table sc_order(
    id integer auto_increment primary key not null ,
    productId Integer default null comment '商品id',
    productName varchar(100) default null comment '商品名称',
    num integer default 0 comment '数量',
    price double default 0.0 comment '价格'
);

select * from sc_order;

insert into sc_order (productId, productName, num, price) VALUE (1, '华为手机p40', 2, 4500);




