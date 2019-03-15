insert into user (username, age, user_id, creator, create_date, updater, update_date)
values ('system', 0, '00000000000000000000000000000000', '00000000000000000000000000000000', now(),
        '00000000000000000000000000000000', now()),
       ('admin', 20, '99ae976445a411e9b210d663bd873d93', '00000000000000000000000000000000', now(),
        '00000000000000000000000000000000', now());
insert into user_auth(user_id, create_date, creator, update_date, updater, authentication_type, credential, identifier)
values ('99ae976445a411e9b210d663bd873d93', now(), '00000000000000000000000000000000', now(),
        '00000000000000000000000000000000', 'USERNAME', '$2a$10$cOSTu5HRErnXYA1dl7eEqe3EwnbUvpnSvcNmLFm4NgJ04JLqb30RG',
        'admin')