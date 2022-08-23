create table assembly_order
(
    id integer not null,
    shop_id varchar(255),
    goods_id varchar(255),
    quantity integer,
    status varchar(255),
    assembler varchar(255),
    tracking_nr varchar(255),
    primary key(id)
);

create table assembly_user
(
    id integer not null,
    role varchar(255),
    name varchar(255),
    password varchar(255),
    primary key(id)
);