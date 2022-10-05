insert into assembly_user (id, role, name ,password) values(1, 'ASSEMBLER', 'John Doe', 'password');
insert into assembly_user (id, role, name ,password) values(2, 'ASSEMBLER', 'Jane Doe', 'password');
insert into assembly_user (id, role, name ,password) values(3, 'ASSEMBLER', 'Gipsz Jakab', 'password');
insert into assembly_user (id, role, name ,password) values(10, 'ADMIN', 'IDo It', 'password');

insert into assembly_order (id, shop_order_id, goods_id ,quantity, status, metadata)            values(3, '2O22/000345', 'printer MK3 from H2Storage', 2, 'NEW'    , '{ "shipping address" : "anywhere"}' );
insert into assembly_order (id, shop_order_id, goods_id ,quantity, status, metadata, assigned_to) values(4, '2O22/000472', 'printer MK3 from H2Storage', 2, 'CLAIMED', '{ "shipping address" : "nowhere"}', 1);
insert into assembly_order (id, shop_order_id, goods_id ,quantity, status, metadata, assigned_to) values(2, '2O22/000563', 'printer MK3 from H2Storage', 2, 'SHIPPED', '{ "shipping address" : "somewhere"}', 1);
insert into assembly_order (id, shop_order_id, goods_id ,quantity, status, metadata) values(1, '2O22/000647', 'printer MK3 from H2Storage', 2, 'NEW', '{ "shipping address" : "there"}');