insert into user (username, password, age, user_id, creator, create_date, updater, update_date)
values ('system', md5('Seaso@2019'), 0, '00000000000000000000000000000000', '00000000000000000000000000000000', now(),
        '00000000000000000000000000000000', now()),
       ('admin', md5('Seaso@2019'), 20, '99ae976445a411e9b210d663bd873d93', '00000000000000000000000000000000', now(),
        '00000000000000000000000000000000', now())