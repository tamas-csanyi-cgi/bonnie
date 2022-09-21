insert into assembly_order (id, shop_id, goods_id ,quantity, status, details) values(3, 'shop 3', 'printer MK3 from H2Storage', 2, 'NEW', '"shippingAddress" : "nowhere"');
insert into assembly_order (id, shop_id, goods_id ,quantity, status, assembler,details) values(1, 'shop 1', 'printer MK3 from H2Storage', 2, 'CLAIMED', '1', '"shippingAddress" :"anywhere"');
insert into assembly_order (id, shop_id, goods_id ,quantity, status, assembler, tracking_nr, details) values(2, 'shop 2', 'printer MK3 from H2Storage', 2, 'SHIPPED', '1', 'original_tracking_nr', '"shippingAddress" : "somewhere"');
insert into assembly_user (id, role, name ,password) values(1, 'ASSEMBLER', 'John Doe', 'password');
insert into assembly_user (id, role, name ,password) values(2, 'SHOP', 'buyIt corp.', 'password');
insert into assembly_user (id, role, name ,password) values(3, 'ADMIN', 'IDo It', 'password');