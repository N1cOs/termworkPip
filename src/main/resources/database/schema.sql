create table student (
  id            serial primary key,
  surname       varchar(50) not null,
  name          varchar(30) not null,
  patronymic    varchar(50),
  date_birth    date not null,
  email         varchar(60) not null unique,
  phone         varchar(20) unique,
  password      varchar(60) not null,
  serial_number varchar(60) not null unique
);

create table subject (
  id   serial primary key,
  name varchar(30) not null unique
);

create table olympiad (
  id            serial primary key,
  id_subj       int references subject (id),
  name          varchar(50) not null unique,
  level         int not null check (level >= 1 and level <= 3),
  serial_number varchar(10) unique
);

create table college (
  id           serial primary key,
  name         varchar(40) not null,
  city         varchar(50) not null,
  abbreviation varchar(10),
  description  text
);

create table speciality (
  id         serial primary key,
  id_college int references college,
  name       varchar(50) not null,
  code_okso  varchar(8)  not null
);

create table speciality_student (
  id_spec         int references speciality on delete cascade on update cascade,
  id_student      int references student on delete cascade on update cascade,
  id_olymp        int references olympiad on update cascade,
  priority        int not null check (priority >= 1 and priority <= 3) default 1,
  originals       boolean not null default false,
  submission_date timestamp not null
);

create table requirements (
  id_spec             int references speciality on delete cascade on update cascade,
  id_subj             int references subject on delete cascade on update cascade,
  min_score           int check (min_score >= 0 and min_score <= 100),
  min_level_olymp_bvi int check (min_level_olymp_bvi <= 3 and min_level_olymp_bvi >= 1)
);

create table worker_ac (
  id          serial primary key,
  id_college  int references college on delete cascade on update cascade,
  surname     varchar(50) not null,
  name        varchar(30) not null,
  patronymic  varchar(50),
  email       varchar(60) not null unique,
  password    varchar(60) not null,
  head_worker boolean not null default false
);

create table messages (
  id_student int references student on delete cascade on update cascade,
  id_worker  int references worker_ac on update cascade,
  message    text not null,
  date timestamp not null
);

create table exam (
  id_student int references student on delete cascade on update cascade,
  id_subj    int references subject on update cascade,
  score      int check (score <= 100 and score >= 0)
);

create table personal_achieves (
  id   serial primary key,
  name varchar(30) not null unique
);

create table per_ach_college (
  id_college     int references college on delete cascade on update cascade,
  id_achievement int references personal_achieves on delete cascade on update cascade,
  score          int not null check (score <= 10 and score > 0)
);

create table per_ach_student (
  id_achivement int references personal_achieves on delete cascade on update cascade,
  id_student    int references student on delete cascade on update cascade
);