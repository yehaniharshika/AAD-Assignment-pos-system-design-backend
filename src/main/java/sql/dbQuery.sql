create database if not exists pos_system;

use pos_system;

create table customer(
    customerId varchar(25) primary key ,
    name varchar(300),
    address varchar(400),
    contactNumber varchar(10),
    email varchar(400)
);

create table item(
    itemCode varchar(25) primary key ,
    itemName varchar(125),
    unitPrice varchar(45),
    qtyOnHand int(10)
);

create table orders(
    orderId varchar(25) primary key ,
    orderDate date not null ,
    customerId varchar(25),
    total varchar(50),
    discount varchar(50),
    subTotal varchar(50),
    cash varchar(60),
    balance varchar(60),
    foreign key (customerId) references customer(customerId) on update cascade on delete cascade
);

create table orderDetails(
    orderId varchar(25),
    itemCode varchar(25),
    qty int(25),
    unitPrice varchar(45),
    total varchar(100),
    foreign key (itemCode) references item(itemCode) on UPDATE cascade on DELETE cascade,
    foreign key (orderId) references orders(orderId) on UPDATE cascade on DELETE cascade
);