insert into user (username, age, user_id, creator, create_date, updater, update_date)
values ('system', 0, 855404942860288, 855404942860288, now(),
        855404942860288, now()),
       ('admin', 20, 855404951248896, 855404951248896, now(),
        855404951248896, now());
insert into user_auth(user_id, create_date, creator, update_date, updater, authentication_type, credential, identifier)
values (855404951248896, now(), 855404942860288, now(),
        855404942860288, 'USERNAME', '$2a$10$cOSTu5HRErnXYA1dl7eEqe3EwnbUvpnSvcNmLFm4NgJ04JLqb30RG',
        'admin')