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
  name          varchar(50) not null,
  level         int not null check (level >= 1 and level <= 3),
  serial_number varchar(10) unique,
  constraint unique_olympiad unique(id_subj, name)
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
  min_level_olymp_bvi int check (min_level_olymp_bvi <= 3 and min_level_olymp_bvi >= 0)
);

create table worker (
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
  id bigserial primary key,
  id_student int not null references student on delete cascade on update cascade,
  id_worker  int not null references worker on update cascade,
  is_from_student boolean not null,
  message    text not null,
  date timestamp not null
);

create table exam (
  id_student int references student on delete cascade on update cascade,
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
  id_student    int references student on delete cascade on update cascade,
  constraint unique_ach_student unique (id_achievement, id_student)
);

create table student_olympiad(
  id_student int references student on delete cascade on update cascade,
  id_olympiad int references olympiad on delete cascade on update cascade,
  constraint unique_olymp unique (id_student, id_olympiad)
);


create or replace function is_valid_email(user_email text, is_student boolean) returns boolean as
$$
declare
  student_row student%rowtype;
  worker_row worker%rowtype;
begin
  if is_student then
    for worker_row in select * from worker loop
      if worke_row.email = user_email then
        return false;
      end if;
    end loop;
  else
    for student_row in select * from student loop
      if student_row.email = user_email then
        return false;
      end if;
    end loop;
  end if;
  return true;
end;
$$ language plpgsql;


create or replace function valid_student_email() returns trigger as
$$
begin
  if not is_valid_email(new.email, true) then
    raise exception 'Invalid student email %, this email belongs to the worker', new.email;
  end if;
  return new;
end;
$$ language plpgsql;

create or replace function valid_worker_email() returns trigger as
$$
begin
  if not is_valid_email(new.email, false) then
    raise exception 'Invalid worker email %, that email belongs to the student', new.email;
  end if;
  return new;
end;
$$ language plpgsql;

create trigger valid_student_email
before insert or update on student
for each row
execute procedure valid_student_email();

create trigger valid_worker_email
before insert or update on worker
for each row
execute procedure valid_worker_email();