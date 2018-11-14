create table abiturient ( 
id_abit serial primary key, 
surname varchar(50), 
name varchar(30), 
patronymic varchar(50), 
date_birth date, 
email varchar(60), 
phone varchar(20), 
password varchar(60), 
serial_number varchar(60) 
); 

create table subject ( 
id_subj serial primary key, 
name varchar(30) 
); 

create table olympiad ( 
id_olymp serial primary key, 
id_subj int references subject (id_subj), 
name varchar(50), 
level int, 
serial_number varchar(10) 
); 

create table speciality_desc ( 
code_okso varchar(8), 
description text 
); 

create table vuz ( 
id_vuz serial primary key, 
name varchar(40), 
city varchar(50), 
abbreviation varchar(10), 
description text 
); 

create table speciality ( 
id_spec serial primary key, 
id_vuz int references vuz (id_vuz), 
name varchar(50), 
code_okso varchar(8) 
); 

create table speciality_abit ( 
id_spec int references speciality (id_spec), 
id_abit int references abiturient (id_abit), 
id_olymp int references olympiad (id_olymp), 
priority int, 
originals boolean, 
submission_date date 
); 

create table requirements ( 
id_spec int references speciality (id_spec), 
id_subj int references subject (id_subj), 
min_score int, 
min_level_olymp_bvi int 
); 

create table worker_ac ( 
id_worker serial primary key , 
id_vuz int references vuz (id_vuz), 
surname varchar(50), 
name varchar(30), 
patronymic varchar(50), 
email varchar(60), 
password varchar(60), 
head_worker boolean 
); 

create table messages ( 
id_abit int references abiturient(id_abit), 
id_worker int references worker_ac(id_worker), 
message text 
); 

create table exam ( 
id_abit int references abiturient(id_abit), 
id_subj int references subject(id_subj), 
score int 
); 

create table individual_achievs ( 
id_subj serial primary key , 
name varchar(30) 
); 

create table ind_ach_vuz( 
id_vuz int references vuz(id_vuz), 
id_achievement int references individual_achievs(id_subj), 
score int 
); 

create table ind_ach_abit( 
id_achivement int references individual_achievs(id_subj), 
id_abit int references abiturient(id_abit) 
);