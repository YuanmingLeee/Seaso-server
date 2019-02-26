create table user
(
  id          int auto_increment
    primary key,
  user_id     char(16)   not null,
  nickname    char(20)   not null,
  avatar      mediumblob null,
  history     longtext   null comment 'user browser history string',
  my_likes    longtext   null comment 'user liked answers string',
  my_dislikes longtext   null comment 'user disliked answers string',
  constraint user_user_id_uindex
    unique (user_id)
)
  comment 'user main table';

create table question
(
  id          int auto_increment,
  question_id char(16)                not null,
  keywords    text                    null comment 'keyword string',
  title       varchar(40)             not null comment 'question title',
  description varchar(100) default '' not null comment 'text description',
  cover       blob                    null comment 'cover image',
  content     mediumblob              null comment 'content',
  viewed      int          default 0  not null comment 'number of views',
  creator     char(16)                not null comment 'creator user id',
  create_date datetime                not null,
  updater     char(16)                not null comment 'updater user id',
  update_date datetime                not null,
  constraint question_id_uindex
    unique (id),
  constraint question_question_id_uindex
    unique (question_id),
  constraint question_user_user_id_fk
    foreign key (creator) references user (user_id),
  constraint question_user_user_id_fk_2
    foreign key (updater) references user (user_id)
);

create table answer
(
  id          int auto_increment,
  answer_id   char(16)      not null,
  question_id char(16)      not null comment 'answer to the question',
  description varchar(100)  not null comment 'cover text',
  cover       blob          null comment 'cover image',
  content     mediumblob    null,
  creator     char(16)      not null comment 'creator user id',
  create_date datetime      not null,
  updater     char(16)      not null comment 'updater user id',
  update_date datetime      not null,
  likes       int default 0 null comment 'number of likes',
  dislikes    int default 0 null comment 'number of dislikes',
  constraint answer_answer_id_uindex
    unique (answer_id),
  constraint answer_id_uindex
    unique (id),
  constraint answer_question_question_id_fk
    foreign key (question_id) references question (question_id),
  constraint answer_user_user_id_fk
    foreign key (creator) references user (user_id),
  constraint answer_user_user_id_fk_2
    foreign key (updater) references user (user_id)
)
  comment 'answer';

alter table answer
  add primary key (id);

create table comments
(
  id          int auto_increment,
  comment_id  char(16) not null,
  answer_id   char(16) not null,
  reply_id    char(16) null,
  creator     char(16) not null,
  create_date datetime not null,
  updater     char(16) not null,
  update_date datetime not null,
  constraint comments_answer_id_uindex
    unique (answer_id),
  constraint comments_comment_id_uindex
    unique (comment_id),
  constraint comments_id_uindex
    unique (id),
  constraint comments_reply_id_uindex
    unique (reply_id),
  constraint comments_answer_answer_id_fk
    foreign key (answer_id) references answer (answer_id),
  constraint comments_comments_comment_id_fk
    foreign key (reply_id) references comments (comment_id),
  constraint comments_user_user_id_fk
    foreign key (creator) references user (user_id),
  constraint comments_user_user_id_fk_2
    foreign key (updater) references user (user_id)
);

alter table comments
  add primary key (id);

alter table question
  add primary key (id);

create table user_auth
(
  id            int auto_increment
    primary key,
  user_id       char(16)                   not null,
  identity_type char(10) default 'USER_ID' not null,
  identifier    varchar(255)               not null,
  credential    varchar(64)                not null,
  constraint user_auth_identifier_uindex
    unique (identifier),
  constraint user_auth_user_id_uindex
    unique (user_id),
  constraint user_auth_user_user_id_fk
    foreign key (user_id) references user (user_id)
)
  comment 'user authentication';


