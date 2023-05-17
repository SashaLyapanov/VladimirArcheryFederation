-- таблица квалификации у Тренеров
DROP TABLE IF EXISTS qualifications;
CREATE TABLE qualifications (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    qualifications_name varchar(250) UNIQUE NOT NULL
);

-- таблица команда
DROP TABLE IF EXISTS teams;
CREATE TABLE teams (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    team_name varchar(250) UNIQUE NOT NULL
);

--таблица типов лука
DROP TABLE IF EXISTS bow_types;
CREATE TABLE bow_types (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    bow_type_name varchar(250) UNIQUE NOT NULL
);

--таблица разрядов
DROP TABLE IF EXISTS sports_titles;
CREATE TABLE sports_titles (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    sports_title_name varchar(250) UNIQUE NOT NULL
);


--таблица юзеров с ролями
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    email varchar(250) NOT NULL UNIQUE,
    user_password varchar(1000) NOT NULL,
    role varchar(25),
    status varchar(25),
    first_name varchar(250),
    last_name varchar(250),
    patronymic varchar(250),
    birth_date date
);

--таблица судий
DROP TABLE IF EXISTS judges;
CREATE TABLE judges (
    id int PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users (id)
);

--таблица админов
DROP TABLE IF EXISTS admins;
CREATE TABLE admins (
    id int PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users (id)
);

-- таблица тренеров
DROP TABLE IF EXISTS coaches;
CREATE TABLE coaches (
    qualification_id int,
    team_id int,
    id int PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users (id),
    FOREIGN KEY (qualification_id) REFERENCES qualifications (id),
    FOREIGN KEY (team_id) REFERENCES teams (id)
);

--таблица для связи тренеров с типами лука, из которого он обучает
DROP TABLE IF EXISTS coache_bow_types;
CREATE TABLE coach_bow_types (
    coach_id int NOT NULL,
    bow_type_id int NOT NULL,
    FOREIGN KEY (coach_id) REFERENCES coaches (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);


--таблица спортсменов
DROP TABLE IF EXISTS sportsmen;
CREATE TABLE sportsmen (
--     id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    team_id int,
    sports_title_id int,
    coach_id int,
    id int PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users (id),
    FOREIGN KEY (team_id) REFERENCES teams (id),
    FOREIGN KEY (sports_title_id) REFERENCES sports_titles (id),
    FOREIGN KEY (coach_id) REFERENCES coaches (id)
);

DROP TABLE IF EXISTS competition_type;
CREATE TABLE competition_type (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    name varchar(30)
);


--таблица соревнований
DROP TABLE IF EXISTS competitions;
CREATE TABLE competitions (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    competition_name varchar(250) NOT NULL,
    place varchar(250) NOT NULL,
    competition_date date,
    status varchar(20),
    judge varchar(250),
    secretary varchar(100),
    zam_judge varchar(100),
    judges varchar(500),
    type_id int,
    FOREIGN KEY (type_id) references competition_type (id)
);


CREATE TABLE categorise (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    name varchar(50)
);

CREATE table competitions_categories (
    competition_id int NOT NULL,
    category_id int NOT NULL,
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (category_id) REFERENCES categorise (id)
);

--таблица для связи соревнований и типов лука в данных соревнованиях
DROP TABLE IF EXISTS competition_bow_type;
CREATE TABLE competition_bow_type (
    competition_id int NOT NULL,
    bow_type_id int NOT NULL,
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

--таблица заявок (является также промежуточной для связи спортсмена и соревнований)
DROP TABLE IF EXISTS applications;
CREATE TABLE applications (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    sportsman_id int NOT NULL,
    competition_id int NOT NULL,
    bow_type_id int NOT NULL,
    payment bool,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

DROP TABLE IF EXISTS achievements;
CREATE TABLE achievements (
     id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
     sportsman_id int NOT NULL,
     competition_id int NOT NULL,
     place int,
     FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
     FOREIGN KEY (competition_id) REFERENCES competitions (id)
);

DROP TABLE IF EXISTS sportsmans_competition;
CREATE TABLE sportsman_competition (
    sportsman_id int NOT NULL,
    competition_id int NOT NULL,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id)
);

-- DROP TABLE IF EXISTS Competition_Bow_type;
-- CREATE TABLE Competition_Bow_type (
--     competition_id int NOT NULL,
--     bow_type_id int NOT NULL,
--     FOREIGN KEY (competition_id) REFERENCES competitions (id),
--     FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
-- );


---------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Реализация таблиц в БД для дневника
---------------------------------------------------------------------------------------------------------------------------------------------------------------------
--таблица типов тренировок (силовая, стрелковая, беговая, ...)
DROP TABLE IF EXISTS session_types;
CREATE TABLE session_types (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    session_type_name varchar(250)
);

--таблица для упражнений в конкретной тренировке
DROP TABLE IF EXISTS session_exercises;
CREATE TABLE session_exercises (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    exercise_name varchar(250) NOT NULL,
    description varchar(500)
);

--таблица тренировок (список записей тренировок, которые насоздавали пользователи
DROP TABLE IF EXISTS sessions;
CREATE TABLE sessions (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    date_time timestamp,
    duration time,
    session_type_id int,
    sportsman_id int NOT NULL,
    session_exercise_id int NOT NULL,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (session_type_id) REFERENCES session_types (id),
    FOREIGN KEY (session_exercise_id) REFERENCES session_exercises (id)
);

--таблица встроенных в систему упражнений
--содержит поле для ссылки на фотку (хранится путь до файла)
DROP TABLE IF EXISTS exercises;
CREATE TABLE exercises (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    exercise_name varchar(250) NOT NULL UNIQUE,
    description varchar(500),
    photo varchar(250)
);

--таблица упражнений, которые пользователь добавляет сам
DROP TABLE IF EXISTS personal_exercises;
CREATE TABLE personal_exercises (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY UNIQUE NOT NULL,
    sportsman_id int NOT NULL,
    exercise_name varchar(250) UNIQUE NOT NULL,
    description varchar (250),
    photo varchar(250)
);


-- --таблица для связи существующих упражнений с конкретной тренировкой
-- DROP TABLE IF EXISTS exercises_sessionExercises;
-- CREATE TABLE exercises_sessionExercises (
--   exercise_id int NOT NULL,
--   session_exercise_id int NOT NULL,
--   FOREIGN KEY (exercise_id) REFERENCES exercises (id),
--   FOREIGN KEY (session_exercise_id) REFERENCES session_exercises (id)
-- );

--таблица для связи пользовательских упражнений с конкретной тренировкой
DROP TABLE IF EXISTS personal_exercises_session_exercises;
CREATE TABLE personalExercises_sessionExercises (
    personal_exercise_id int NOT NULL,
    session_exercise_id int NOT NULL,
    FOREIGN KEY (personal_exercise_id) REFERENCES personal_exercises (id),
    FOREIGN KEY (session_exercise_id) REFERENCES session_exercises (id)
);


--таблица для связи таблиц записей тренировок с конкретной тренировкой (которая содержит набор упражнений)
DROP TABLE IF EXISTS session_session_exercises;
CREATE TABLE session_sessionExercises (
    session_id int NOT NULL,
    session_exercise_id int NOT NULL,
    FOREIGN KEY (session_id) REFERENCES sessions (id),
    FOREIGN KEY (session_exercise_id) REFERENCES session_exercises (id)
);


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Вставка данных для начальной работы

-- Создание админа
insert into users (email, user_password, role, status, first_name, last_name, patronymic, birth_date)
values ('admin@mail.ru', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'ADMIN', 'ACTIVE', 'admin', 'admin', 'admin', '2002-10-28');
insert into admins (id) values (1);

-- создание квалификаций
insert into qualifications (qualifications_name) values ('Начинающий');
insert into qualifications (qualifications_name) values ('Профессионал');
insert into qualifications (qualifications_name) values ('Мастер');

-- создание команды
insert into teams (team_name) values ('Команда 1');
insert into teams (team_name) values ('Команда 2');
insert into teams (team_name) values ('Команда 3');

-- создание спортивных титулов
insert into sports_titles (sports_title_name) values ('1 взрослый');
insert into sports_titles (sports_title_name) values ('КМС');
insert into sports_titles (sports_title_name) values ('МС');
insert into sports_titles (sports_title_name) values ('МСМК');
insert into sports_titles (sports_title_name) values ('ЗМС');

-- Создание тренера
insert into users (email, user_password, role, status, first_name, last_name, patronymic, birth_date)
values ('coach@mail.ru', '$2a$12$j4JRYkonMcSwbj/huP.gcu7dCLgnWZdMM.iiTv0YUbGOr.1646WYW', 'COACH', 'ACTIVE', 'coach', 'coach', 'coach', '1970-01-20');
insert into coaches (qualification_id, team_id, id)
values (1, 1, 2);

-- Создание спортсмена
insert into users (email, user_password, role, status, first_name, last_name, patronymic, birth_date)
values ('sportsman@mail.ru', '$2a$12$FbG/RhA5yloQfG7vKFKo8.EJQd8Ob0wiHdmVMPNDnB4ZVk0cDF7xy', 'SPORTSMAN', 'ACTIVE', 'sportsman', 'sportsman', 'sportsman', '2003-01-20');
insert into sportsmen (team_id, sports_title_id, coach_id, id)
values (1, 1, 2, 3);

-- Создание типов лука
insert into bow_types (bow_type_name)
values ('Длинный лук');
insert into bow_types (bow_type_name)
values ('Блочный лук');
insert into bow_types (bow_type_name)
values ('Монгольский лук');
insert into bow_types (bow_type_name)
values ('Составной лук');

-- Создание категорий (возрастные+пол) для соревнований
insert into categorise (name)
values ('Мужчины 14+');
insert into categorise (name)
values ('Женщины 14+');
insert into categorise (name)
values ('Мальчики до 14 лет');
insert into categorise (name)
values ('Девочки 14 лет');
insert into categorise (name)
values ('Юноши до 18 лет');
insert into categorise (name)
values ('Девушки до 18 лет');
insert into categorise (name)
values ('Юниоры до 21 года');
insert into categorise (name)
values ('Юниорки до 21 года');
insert into categorise (name)
values ('Мужчины');
insert into categorise (name)
values ('Женщины');


-- Создание типов соревнований (3Д, таргет)
insert into competition_type (name)
values ('3D');
insert into competition_type (name)
values ('Target archery');