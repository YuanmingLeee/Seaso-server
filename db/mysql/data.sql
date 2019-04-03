INSERT INTO test.sys_role (create_date, creator, update_date, updater, role_type)
VALUES (NOW(), 0, NOW(), 0, 'ADMIN'),
       (NOW(), 0, NOW(), 0, 'USER'),
       (NOW(), 0, NOW(), 0, 'GUEST');

# add system and admin
INSERT INTO test.user (create_date, creator, update_date, updater, age, avatar, history, my_dislikes, my_likes,
                       user_id, username)
VALUES (NOW(), 0, NOW(), 0, NULL, NULL, '', '', '', 0, 'SYSTEM'),
       (NOW(), 0, NOW(), 0, NULL, NULL, '', '', '', 1, 'admin');
INSERT INTO test.sys_user (create_date, creator, update_date, updater, user_id) VALUE
  (NOW(), 0, NOW(), 0, 1);
INSERT INTO test.sys_auth (create_date, creator, update_date, updater, authentication_type, credential, identifier,
                           user_id)
  VALUE (NOW(), 0, NOW(), 0, 'USERNAME',
         '$2a$10$cOSTu5HRErnXYA1dl7eEqe3EwnbUvpnSvcNmLFm4NgJ04JLqb30RG', 'admin', 1);
INSERT INTO test.user_authentication (authentication_identifier, user_id) VALUE ('admin', 1);

# assign role to admin
SELECT id INTO @admin_id
FROM test.sys_user
WHERE user_id = 1;
SELECT id INTO @role_id
FROM test.sys_role
WHERE role_type = 'ADMIN';
INSERT INTO test.sys_user_role (user_id, role_id) VALUE (@admin_id, @role_id);
