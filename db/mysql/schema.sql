create schema test collate utf8mb4_0900_ai_ci;

create table question
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     varchar(32)  not null,
  update_date datetime     not null,
  updater     varchar(32)  not null,
  content     longblob     null,
  cover       longblob     null,
  description varchar(100) null,
  keywords    longtext     null,
  question_id varchar(32)  not null,
  title       varchar(40)  null,
  views       bigint       null,
  constraint question_question_id_uindex
    unique (question_id)
);

create table answer
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     varchar(32)  not null,
  update_date datetime     not null,
  updater     varchar(32)  not null,
  answer_id   varchar(32)  not null,
  content     longblob     null,
  cover       longblob     null,
  description varchar(100) null,
  dislikes    bigint       null,
  likes       bigint       null,
  question_id varchar(32)  not null,
  constraint answer_answer_id_uindex
    unique (answer_id),
  constraint answer_question_question_id_fk
    foreign key (question_id) references question (question_id)
);

create table comment
(
  id          bigint auto_increment
    primary key,
  create_date datetime     not null,
  creator     varchar(32)  not null,
  update_date datetime     not null,
  updater     varchar(32)  not null,
  comment_id  varchar(32)  not null,
  content     varchar(240) null,
  answer_id   varchar(32)  not null,
  reply_id    varchar(32)  null,
  constraint comment_comment_id_uindex
    unique (comment_id),
  constraint comment_answer_answer_id_fk
    foreign key (answer_id) references answer (answer_id),
  constraint comment_comment_id_fk
    foreign key (reply_id) references comment (comment_id)
);

create table user
(
  id          bigint auto_increment
    primary key,
  create_date datetime    not null,
  creator     varchar(32) not null,
  update_date datetime    not null,
  updater     varchar(32) not null,
  age         int         not null,
  avatar      longblob    null,
  history     longtext    null,
  my_dislikes longtext    null,
  my_likes    longtext    null,
  user_id     varchar(32) not null,
  username    varchar(32) not null,
  constraint user_user_id_uindex
    unique (user_id)
);

create table user_auth
(
  id                  bigint auto_increment
    primary key,
  create_date         datetime     not null,
  creator             varchar(32)  not null,
  update_date         datetime     not null,
  updater             varchar(32)  not null,
  authentication_type varchar(255) null,
  credential          varchar(255) not null,
  identifier          varchar(255) not null,
  user_id             varchar(32)  not null,
  constraint UK_k0okg0ovq2a68n6onl48bvdrh
    unique (identifier),
  constraint user_auth_user_user_id_fk
    foreign key (user_id) references user (user_id)
);
