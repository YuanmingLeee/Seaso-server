create table test.answer
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     bigint       not null,
  update_date datetime     not null,
  updater     bigint       not null,
  answer_id   bigint       not null,
  content     longblob     null,
  cover       longblob     null,
  description varchar(100) null,
  dislikes    bigint       null,
  likes       bigint       null,
  question_id bigint       not null,
  constraint UK_jn4639k8ac48dx8abh52ltc8h
    unique (answer_id)
);

create table test.comment
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     bigint       not null,
  update_date datetime     not null,
  updater     bigint       not null,
  answer_id   bigint       not null,
  comment_id  bigint       not null,
  content     varchar(240) null,
  reply_id    bigint       null,
  constraint UK_9k154dwhlnk39nh50ix50swor
    unique (comment_id)
);

create table test.question
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     bigint       not null,
  update_date datetime     not null,
  updater     bigint       not null,
  content     longblob     null,
  cover       longblob     null,
  description varchar(100) null,
  keywords    longtext     null,
  question_id bigint       not null,
  title       varchar(40)  null,
  views       bigint       null,
  constraint UK_488pr2mfs6ye2bjlwf0pbcynf
    unique (question_id)
);

create table test.sys_role
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     bigint       not null,
  update_date datetime     not null,
  updater     bigint       not null,
  role_type   varchar(255) null
);

create table test.user
(
  id          bigint auto_increment
    primary key,
  create_date datetime    not null,
  creator     bigint      not null,
  update_date datetime    not null,
  updater     bigint      not null,
  age         int         null,
  avatar      longblob    null,
  history     longtext    null,
  my_dislikes longtext    null,
  my_likes    longtext    null,
  user_id     bigint      not null,
  username    varchar(32) not null,
  constraint user_user_id_uindex
    unique (user_id)
);

create table test.sys_user
(
  id          bigint auto_increment
    primary key,
  create_date datetime not null,
  creator     bigint   not null,
  update_date datetime not null,
  updater     bigint   not null,
  user_id     bigint   not null,
  constraint UK_onpujjcyjmvs0121ehqanl2kq
    unique (user_id),
  constraint FKlr3bpgs4b9sodg2y0axap3gs4
    foreign key (user_id) references test.user (user_id)
);

create table test.sys_auth
(
  id                  bigint auto_increment
    primary key,
  create_date         datetime     not null,
  creator             bigint       not null,
  update_date         datetime     not null,
  updater             bigint       not null,
  authentication_type varchar(255) null,
  credential          varchar(255) not null,
  identifier          varchar(255) not null,
  user_id             bigint       not null,
  constraint user_auth_identifier_unidex
    unique (identifier),
  constraint sys_user_auth_user_id_sys_user_user_id_fk
    foreign key (user_id) references test.sys_user (user_id)
);

create table test.sys_user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint UK_fethvr269t6stivlddbo5pxry
    unique (user_id),
  constraint UK_fxu3td9m5o7qov1kbdvmn0g0x
    unique (role_id),
  constraint FKb40xxfch70f5qnyfw8yme1n1s
    foreign key (user_id) references test.sys_user (id),
  constraint FKhh52n8vd4ny9ff4x9fb8v65qx
    foreign key (role_id) references test.sys_role (id)
);

create table test.user_authentication
(
  id                        bigint auto_increment
    primary key,
  authentication_identifier varchar(255) null,
  user_id                   bigint       null,
  constraint user_authen_authen_identifier_fk
    foreign key (authentication_identifier) references test.sys_auth (identifier),
  constraint user_authen_user_user_id_fk
    foreign key (user_id) references test.user (user_id)
);

