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
  constraint question_question_id_uindex
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
  constraint FKsct63i6ksinu8c2dv7fh4nff4
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
  constraint UK_2ps4cm81b5drtp4qgkxrbs3dx
    unique (identifier),
  constraint sys_user_auth_user_id_sys_user_id_fk
    foreign key (user_id) references test.sys_user (id)
);

create table test.sys_user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint UK_fethvr269t6stivlddbo5pxry
    unique (user_id),
  constraint UK_fxu3td9m5o7qov1kbdvmn0g0x
    unique (role_id),
  constraint sys_user_role_sys_role_id_fk
    foreign key (role_id) references test.sys_role (id),
  constraint sys_user_role_sys_user_id_fk
    foreign key (user_id) references test.sys_user (id)
);

