DELETE FROM ORDERS;
DELETE FROM user_roles;
DELETE FROM INSTALLER;
DELETE FROM users;

INSERT INTO users (id, nickname, invite_code, hints) VALUES
    ('55555555-5555-4555-8555-555555555555', 'Алексей Смирнов', 'a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2',true);

INSERT INTO users (id, nickname, invite_code, hints) VALUES
    ('66666666-6666-4666-8666-666666666666', 'installer_user2', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', true);

INSERT INTO INSTALLER (id, full_name, phone, user_id) VALUES ('a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6', 'Алексей Смирнов', '+79231246800', '55555555-5555-4555-8555-555555555555');
INSERT INTO INSTALLER (id, full_name, phone,  user_id) VALUES ('f7e6d5c4-b3a2-4109-9876-543210fedcba', 'Павел Деревянка', '+79994673033', '66666666-6666-4666-8666-666666666666');

INSERT INTO user_roles (user_id, role) VALUES ('55555555-5555-4555-8555-555555555555', 'ROLE_INSTALLER');
INSERT INTO user_roles (user_id, role) VALUES ('66666666-6666-4666-8666-666666666666', 'ROLE_INSTALLER');