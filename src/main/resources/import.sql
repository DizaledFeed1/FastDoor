INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('55555555-5555-4555-8555-555555555555', 'installer_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Иван Петров', true);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('66666666-6666-4666-8666-666666666666', 'installer_user2', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Павел Деревянка', true);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('44444444-4444-4444-4444-666666666664', 'installer_user3', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Павел Воля', true);

INSERT INTO installer (id, full_name, phone, user_id) VALUES ('a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6','Иван Петров', '+79231246800', '55555555-5555-4555-8555-555555555555');
INSERT INTO installer (id, full_name, phone, user_id) VALUES ('f7e6d5c4-b3a2-4109-9876-543210fedcba','Павел Деревянка', '+79994673033', '66666666-6666-4666-8666-666666666666');
INSERT INTO installer (id, full_name, phone, user_id) VALUES ('12345678-90ab-4cde-f012-34567890abcd','Павел Воля', '+79137488501', '44444444-4444-4444-4444-666666666664');

INSERT INTO user_roles (user_id, role) VALUES ('55555555-5555-4555-8555-555555555555', 'ROLE_INSTALLER');
INSERT INTO user_roles (user_id, role) VALUES ('66666666-6666-4666-8666-666666666666', 'ROLE_INSTALLER');
INSERT INTO user_roles (user_id, role) VALUES ('44444444-4444-4444-4444-666666666664', 'ROLE_INSTALLER');

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
