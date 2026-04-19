INSERT INTO installer (id, full_name, phone) VALUES ('a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6','Иван Петров', '+79231246800');
INSERT INTO installer (id, full_name, phone) VALUES ('f7e6d5c4-b3a2-4109-9876-543210fedcba','Павел Деревянка', '+79994673033');
INSERT INTO installer (id, full_name, phone) VALUES ('12345678-90ab-4cde-f012-34567890abcd','Павел Воля', '+79137488501');
--
insert into "ORDERS" (address, phone, DATE_ORDER ,  inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-06', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-11', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-01', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-01', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-01', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-03-01', '70','70');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', '10','10', 'f7e6d5c4-b3a2-4109-9876-543210fedcba');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', '10','10', 'f7e6d5c4-b3a2-4109-9876-543210fedcba');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', '20','20', '12345678-90ab-4cde-f012-34567890abcd');
insert into "ORDERS" (address, phone, DATE_ORDER  , inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', '10','10', '12345678-90ab-4cde-f012-34567890abcd');
