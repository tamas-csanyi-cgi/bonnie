insert into assembly_order (id, goods_id ,quantity, status) values(3, 'printer MK3 from H2Storage', 2, 'NEW');
insert into assembly_order (id, goods_id ,quantity, status, assembler) values(1, 'printer MK3 from H2Storage', 2, 'CLAIMED', 1);
insert into assembly_order (id, goods_id ,quantity, status, assembler) values(2, 'printer MK3 from H2Storage', 2, 'SHIPPED', 1);
insert into assembly_user (user_id, role, name ,password) values(1, 'ASSEMBLER', 'John Doe', 'password');
insert into assembly_user (user_id, role, name ,password) values(2, 'ASSEMBLER', 'Jane Doe', 'password');
insert into assembly_user (user_id, role, name ,password) values(3, 'ASSEMBLER', 'Gipsz Jakab', 'password');
insert into assembly_user (user_id, role, name ,password) values(10, 'ADMIN', 'IDo It', 'password');