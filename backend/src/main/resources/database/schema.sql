create table app_user(
  id serial primary key,
  type int not null,
  surname       varchar(50) not null,
  name          varchar(30) not null,
  patronymic    varchar(50),
  email         varchar(60) not null unique,
  phone         varchar(20) unique,
  password      varchar(60) not null,
  is_enabled    boolean default true,
--   Worker's fields
  id_college    int references college on delete cascade on update cascade,
--   Student's fields
  date_birth    date,
  serial_number varchar(10) unique
);

create table college (
  id           serial primary key,
  name         varchar(40) not null,
  city         varchar(50) not null,
  abbreviation varchar(10),
  description  text
);

create table authority(
  id serial primary key check (id in (1, 2, 3)),
  name varchar(40) not null
);

create table user_authority(
  id_user int references app_user,
  id_authority int references authority
);

create table subject (
  id   serial primary key,
  name varchar(30) not null unique
);

create table olympiad (
  id            serial primary key,
  id_subj       int references subject,
  name          varchar(50) not null,
  level         int not null check (level >= 1 and level <= 3),
  serial_number varchar(10) unique,
  constraint unique_olympiad unique(id_subj, name)
);

create table speciality (
  id         serial primary key,
  id_college int references college,
  name       varchar(50) not null,
  code_okso  varchar(8)  not null
);

create table speciality_student (
  id_spec         int references speciality on delete cascade on update cascade,
  id_student      int references app_user on delete cascade on update cascade,
  id_olymp        int references olympiad on update cascade,
  priority        int not null check (priority >= 1 and priority <= 3) default 1,
  originals       boolean not null default false,
  submission_date timestamp not null
);

create table requirement (
  id_spec             int references speciality on delete cascade on update cascade,
  id_subj             int references subject on delete cascade on update cascade,
  min_score           int check (min_score >= 0 and min_score <= 100),
  min_level_olymp_bvi int check (min_level_olymp_bvi <= 3 and min_level_olymp_bvi >= 0)
);

create table message (
  id_user_1 int references app_user,
  id_user_2 int references app_user,
  id_sender int references app_user,
  message_text   text not null,
  date timestamp not null
);

create table exam (
  id_student int references app_user on delete cascade on update cascade,
  id_subj    int references subject on update cascade,
  score      int check (score <= 100 and score >= 0),
  constraint unique_exam unique (id_student, id_subj)
);

create table achievement (
  id   serial primary key,
  name varchar(30) not null unique
);

create table ach_college (
  id_college     int references college on delete cascade on update cascade,
  id_achievement int references achievement on delete cascade on update cascade,
  score          int not null check (score <= 10 and score > 0),
  constraint unique_ach_college unique (id_college, id_achievement)
);

create table ach_student (
  id_achievement int references achievement on delete cascade on update cascade,
  id_student    int references app_user on delete cascade on update cascade,
  constraint unique_ach_student unique (id_achievement, id_student)
);

create table student_olympiad(
  id_student int references app_user on delete cascade on update cascade,
  id_olympiad int references olympiad on delete cascade on update cascade,
  constraint unique_olymp unique (id_student, id_olympiad)
);

create or replace function valid_user() returns trigger as
$$
begin
  -- value 2 means worker, 1 means student
  if new.type = 2 then
    if (not new.date_birth is null) or (not new.serial_number is null) then
      raise exception 'Worker can''t have date_birth or serial_number';
    end if;
  elseif new.type = 1 then
    if new.serial_number is null or new.date_birth is null then
      raise exception 'Student must have birth date and serial number';
    end if;
  else
    raise exception 'Type value can be 1 or 2';
  end if;
  return new;
  end;
$$ language plpgsql;

create trigger valid_user
  before insert or update on app_user
  for each row
execute procedure valid_user();