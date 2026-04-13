DELETE FROM ORDERS;
DELETE FROM user_roles;
DELETE FROM INSTALLER;
DELETE FROM users;

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('11111111-1111-4111-8111-111111111111', 'seller_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Магазин "Цветы"', true);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('22222222-2222-4222-8222-222222222222', 'main_installer_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Иван Петров', true);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('33333333-3333-4333-8333-333333333333', 'admin_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'System Admin', false);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('44444444-4444-4444-8444-444444444444', 'service_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Сервис Центр', true);

INSERT INTO users (id, username, password, nickname, hints) VALUES
    ('55555555-5555-4555-8555-555555555555', 'installer_user', '$2a$10$X.vZ.kZ.qZ.qZ.qZ.qZ.qO', 'Алексей Смирнов', true);

INSERT INTO INSTALLER (id, full_name, phone, user_id) VALUES ('a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6', 'Алексей Смирнов', '+79231246800', '55555555-5555-4555-8555-555555555555');
INSERT INTO INSTALLER (id, full_name, phone) VALUES ('f7e6d5c4-b3a2-4109-9876-543210fedcba', 'Павел Деревянка', '+79994673033');
INSERT INTO INSTALLER (id, full_name, phone) VALUES ('12345678-90ab-4cde-f012-34567890abcd', 'Павел Воля', '+79137488501');

INSERT INTO user_roles (user_id, role) VALUES ('11111111-1111-4111-8111-111111111111', 'ROLE_SELLER');
INSERT INTO user_roles (user_id, role) VALUES ('22222222-2222-4222-8222-222222222222', 'ROLE_MAIN_INSTALLER');
INSERT INTO user_roles (user_id, role) VALUES ('33333333-3333-4333-8333-333333333333', 'ROLE_ADMIN');
INSERT INTO user_roles (user_id, role) VALUES ('44444444-4444-4444-8444-444444444444', 'ROLE_SERVICES');
INSERT INTO user_roles (user_id, role) VALUES ('55555555-5555-4555-8555-555555555555', 'ROLE_INSTALLER');

insert into ORDERS (address, phone, DATE_ORDER, condition, inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', 'WORKED','10','10', 'a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6');
insert into ORDERS (address, phone, DATE_ORDER, condition, inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', 'WORKED','10','10', 'a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6');
insert into ORDERS (address, phone, DATE_ORDER, condition, inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', 'ASSIGNED','20','20', 'a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6');
insert into ORDERS (address, phone, DATE_ORDER, condition, inDoorQuantity, frontDoorQuantity, installer_id) values ('Ул. Пушкина, д. Колатушкина', '88005553535', '2025-07-10', 'COMPLETED','10','10', 'a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6');