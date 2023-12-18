-- таблица Пол спортсмена
DROP TABLE IF EXISTS sex;
CREATE TABLE sex (
                     id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                     name varchar(10)
);

--таблица типов лука
DROP TABLE IF EXISTS bow_types;
CREATE TABLE bow_types (
                           id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                           bow_type_name varchar(250) UNIQUE NOT NULL
);

--таблица разрядов
DROP TABLE IF EXISTS sports_titles;
CREATE TABLE sports_titles (
                               id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                               sports_title_name varchar(250) UNIQUE NOT NULL
);


--таблица юзеров с ролями
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                       email varchar(250) NOT NULL UNIQUE,
                       password varchar(1000) NOT NULL,
                       role varchar(25),
                       status varchar(25),
                       name varchar(250),
                       surname varchar(250),
                       patronymic varchar(250),
                       birth_date date
);

--таблица судий
DROP TABLE IF EXISTS judges;
CREATE TABLE judges (
                        id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                        FOREIGN KEY (id) REFERENCES users (id)
);

--таблица админов
DROP TABLE IF EXISTS admins;
CREATE TABLE admins (
                        id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                        FOREIGN KEY (id) REFERENCES users (id)
);

--таблица для хранения всех субъектов
CREATE TABLE regions (
                         id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                         name varchar(100)
);

--таблица спортсменов
DROP TABLE IF EXISTS sportsmen;
CREATE TABLE sportsmen (
                           id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                           club varchar(100),
                           avatar_image varchar(256),
                           sex_id varchar(36),
                           sports_title_id varchar(36),
                           region_id varchar(36),
                           FOREIGN KEY (id) REFERENCES users (id),
                           FOREIGN KEY (sex_id) REFERENCES sex (id),
                           FOREIGN KEY (sports_title_id) REFERENCES sports_titles (id),
                           FOREIGN KEY (region_id) REFERENCES regions (id)
);

DROP TABLE IF EXISTS competition_type;
CREATE TABLE competition_type (
                                  id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                                  name varchar(30)
);

--таблица соревнований
DROP TABLE IF EXISTS competitions;
CREATE TABLE competitions (
                              id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                              competition_name varchar(250) NOT NULL,
                              place varchar(250) NOT NULL,
                              competition_date date,
                              status varchar(20),
                              judge varchar(250),
                              secretary varchar(100),
                              zam_judge varchar(100),
                              judges varchar(500),
                              type_id varchar(36),
                              pdf_file varchar(250),
                              FOREIGN KEY (type_id) references competition_type (id)
);


CREATE TABLE categories (
                            id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                            name varchar(50)
);

CREATE table competitions_categories (
                                         competition_id varchar(36) NOT NULL,
                                         category_id varchar(36) NOT NULL,
                                         FOREIGN KEY (competition_id) REFERENCES competitions (id),
                                         FOREIGN KEY (category_id) REFERENCES categories (id)
);

--таблица для связи соревнований и типов лука в данных соревнованиях
DROP TABLE IF EXISTS competition_bow_type;
CREATE TABLE competition_bow_type (
                                      competition_id varchar(36) NOT NULL,
                                      bow_type_id varchar(36) NOT NULL,
                                      FOREIGN KEY (competition_id) REFERENCES competitions (id),
                                      FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

--таблица заявок (является также промежуточной для связи спортсмена и соревнований)
DROP TABLE IF EXISTS applications;
CREATE TABLE applications (
                              id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                              sportsman_id varchar(36) NOT NULL,
                              competition_id varchar(36) NOT NULL,
                              bow_type_id varchar(36) NOT NULL,
                              payment bool,
                              FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
                              FOREIGN KEY (competition_id) REFERENCES competitions (id),
                              FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

DROP TABLE IF EXISTS achievements;
CREATE TABLE achievements (
                              id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
                              sportsman_id varchar(36) NOT NULL,
                              competition_id varchar(36) NOT NULL,
                              place int,
                              FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
                              FOREIGN KEY (competition_id) REFERENCES competitions (id)
);

DROP TABLE IF EXISTS sportsmans_competition;
CREATE TABLE sportsman_competition (
                                       sportsman_id varchar(36) NOT NULL,
                                       competition_id varchar(36) NOT NULL,
                                       FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
                                       FOREIGN KEY (competition_id) REFERENCES competitions (id)
);

DROP TABLE IF EXISTS articles;
CREATE TABLE articles (
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(50) NOT NULL,
    body varchar (1000),
    date date,
    preview_image_id varchar(36)
);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Вставка данных для начальной работы

-- создание таблицы Пол спортсмена
insert into sex (id, name) values ('c99ccd51-5731-42a3-9cfc-31cc4011e035','Мужской');
insert into sex (id, name) values ('848f4054-a9c1-4525-9e10-2ab07e3e9b4c','Женский');

-- Создание админа
insert into users (id, email, password, role, status, name, surname, patronymic, birth_date)
values ('71b0af32-887c-4903-bc54-af3f96481e9e','admin@mail.ru', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'ADMIN', 'ACTIVE', 'admin', 'admin', 'admin', '2002-10-28');
insert into admins (id) values ('71b0af32-887c-4903-bc54-af3f96481e9e');

-- создание спортивных титулов
insert into sports_titles (id, sports_title_name) values ('5a19807c-0630-435b-a0b8-5158ad900456','1 взрослый');
insert into sports_titles (id, sports_title_name) values ('6ac053fd-8568-4a36-ae7c-741980d72684','КМС');
insert into sports_titles (id, sports_title_name) values ('36be5498-fd85-4525-b9b6-57abd91c3666','МС');
insert into sports_titles (id, sports_title_name) values ('049adae2-b4a7-4f65-aa54-9c3cae532c6a','МСМК');
insert into sports_titles (id, sports_title_name) values ('dd015fc-da4f-4e6e-a7f8-9052584d7fab','ЗМС');

-- Создание типов лука
insert into bow_types (id, bow_type_name)
values ('af44dbd5-21bb-41f1-b732-af5706b8153d', 'Длинный лук');
insert into bow_types (id, bow_type_name)
values ('62cb799b-0ff8-4843-82c0-61a215d4af97', 'Блочный лук');
insert into bow_types (id, bow_type_name)
values ('ac6a3094-b354-4ebd-8bb1-19111742c764', 'Монгольский лук');
insert into bow_types (id, bow_type_name)
values ('351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2', 'Составной лук');

-- Создание категорий (возрастные+пол) для соревнований
insert into categories (id,name)
values ('d146475e-681f-4188-a814-c8fb44b416b1','Мужчины 14+');
insert into categories (id,name)
values ('35b34c98-acef-4a51-904d-4db53a2cc4f6','Женщины 14+');
insert into categories (id,name)
values ('79a91a6c-a760-45b7-a241-c11b64b424fa','Мальчики до 14 лет');
insert into categories (id,name)
values ('2dee7343-135f-4d7b-bb35-ec7bb3424359','Девочки 14 лет');
insert into categories (id,name)
values ('cf98d4a2-4fd1-4084-8de5-4e70cf20f9c0','Юноши до 18 лет');
insert into categories (id,name)
values ('89d3426c-93fb-419b-a0ac-1b4cc0971643','Девушки до 18 лет');
insert into categories (id,name)
values ('c22c8805-0c4f-48b3-b357-da196b3cf95d','Юниоры до 21 года');
insert into categories (id,name)
values ('01f765b4-241f-4c9a-8798-7bb162e3ea40','Юниорки до 21 года');
insert into categories (id,name)
values ('7c1d9beb-9eb2-4542-ad67-8ef2b53d3af4','Мужчины');
insert into categories (id,name)
values ('55b3b2f1-5a09-4dee-a249-b4d76d0444d6','Женщины');


-- Создание типов соревнований (3Д, таргет)
insert into competition_type (id, name)
values ('e173e1ac-b01e-4cfb-8b42-e4bbd1f2a180','3D');
insert into competition_type (id, name)
values ('e7d55d2b-3ac1-47fd-93d0-c0d11cab4b95','Target archery');


-- Заполнение таблицы регионов
insert into regions (id,name) values ('bf9614ff-a9d2-4371-a97c-8c04383ae2e3', 'Алтайский край');
insert into regions (id,name) values ('652af983-eec9-4738-8107-1d61c37bf201', 'Амурская область');
insert into regions (id,name) values ('4d33d410-4a14-438d-b221-13eff85c6b9a', 'Архангельская область');
insert into regions (id,name) values ('0eacdc58-f127-47ad-b840-8de31db39303', 'Астраханская область');
insert into regions (id,name) values ('89a0d3a4-b613-44c2-97a0-767ab9ce145a', 'Белгородская область');
insert into regions (id,name) values ('5162f2d4-6fd5-4ac5-a611-f52f8bd0f33c', 'Брянская область');
insert into regions (id,name) values ('b14c7476-fdb9-4d3d-9338-33c846b1042f', 'Владимирская область');
insert into regions (id,name) values ('50e69125-4c9a-4a3a-8655-a01afe372a27', 'Волгоградская область');
insert into regions (id,name) values ('6598f7a5-060b-4b86-a58b-543c71246cda', 'Вологодская область');
insert into regions (id,name) values ('48b3abae-3a5b-4382-b534-a4bfebc09cc7', 'Воронежская область');

insert into regions (id,name) values ('635198fd-1fb9-4b3e-96ea-c9fafb773b3c', 'Москва');
insert into regions (id,name) values ('8461cce4-18cc-43f1-bb31-abce519d8151', 'Санкт-Петербург');
insert into regions (id,name) values ('8d766853-e6bb-45f1-bfb6-977f8d26587b', 'Севастополь');
insert into regions (id,name) values ('4237f4f8-b818-4399-918c-2a4c4bb9fd77', 'ДНР');
insert into regions (id,name) values ('6894c6a4-6f4f-4191-a34f-4875679f4f23', 'Еврейская автономная область');
insert into regions (id,name) values ('fa2fbe06-0d74-45eb-95b2-a36db7f74b52', 'Забайкальский край');
insert into regions (id,name) values ('079a3abe-8d8b-44d5-a588-9830e77960b0', 'Запорожская область');
insert into regions (id,name) values ('0c0602d9-a4bf-4e3c-838a-429cdbae311c', 'Ивановская область');
insert into regions (id,name) values ('18c1ed1e-5805-48bf-8830-8edddd998382', 'Иркутская область');
insert into regions (id,name) values ('b187f513-590a-4897-bb9a-25b9796d1a4c', 'Кабардино-Балкарская Республика');

insert into regions (id,name) values ('c226be90-53b7-4c86-9543-bd8978119631', 'Калининградская область');
insert into regions (id,name) values ('9050fcff-1aa7-4ad3-9182-10e50dfea57f', 'Калужская область');
insert into regions (id,name) values ('c3789190-dff3-4570-8fc4-5519acc8b0f3', 'Камчатский край');
insert into regions (id,name) values ('4885e81c-277d-489e-a5a7-f272f5031d63', 'Карачаево-Черкесская Республика');
insert into regions (id,name) values ('aceea942-3ccc-4180-9d1b-58c0670ee758', 'Кемеровская область');
insert into regions (id,name) values ('03e5ee78-84e3-4951-b67c-fc1d20915f6e', 'Кировская область');
insert into regions (id,name) values ('a0ed1653-b004-4ed3-b00d-723e20f82d03', 'Костромская область');
insert into regions (id,name) values ('88001452-dcea-46ad-bdcb-7b4385b0bf2a', 'Краснодарский край');
insert into regions (id,name) values ('87300847-80a1-4500-ad02-d1e9d9439fde', 'Красноярский край');
insert into regions (id,name) values ('cdc3078f-faa4-4e31-b773-0daba6f966bd', 'Курганская область');

insert into regions (id,name) values ('62bc6167-5d7b-4c36-8e38-8be5f0c57be3', 'Курская область');
insert into regions (id,name) values ('448468e3-5b3d-4413-9d53-5ca49e6bf91e', 'Ленинградская область');
insert into regions (id,name) values ('923e0cc8-6b73-4c32-af4d-00bc84d4cfca', 'Липецкая область');
insert into regions (id,name) values ('b99c0c2e-c35a-465c-ab26-181db8ad04af', 'ЛНР');
insert into regions (id,name) values ('c5232f40-2660-4b92-847f-d7a6a83a7680', 'Магаданская область');
insert into regions (id,name) values ('223184f7-a427-49f3-bf3b-0a10c3ed3ca1', 'Московская область');
insert into regions (id,name) values ('80450e39-1108-48a1-9520-0e2823846814', 'Мурманская область');
insert into regions (id,name) values ('b2e8b76a-3fb0-49c0-90f3-eb953cfa13b3', 'Ненецкий автономный округ');
insert into regions (id,name) values ('29352fe5-b966-4274-bd19-660e4478227f', 'Нижегородская область');
insert into regions (id,name) values ('6d222557-650e-4d23-b3d1-2329145ce7e5', 'Новгородская область');

insert into regions (id,name) values ('5d415fc0-a748-442b-9c8e-8107e6e28c86', 'Новосибирская область');
insert into regions (id,name) values ('397f6907-1dd8-4ef4-8829-aa1d23dbccb3', 'Омская область');
insert into regions (id,name) values ('761defd3-2f5b-48de-81a5-4195907b087b', 'Оренбургская область');
insert into regions (id,name) values ('74a31c3c-ba4a-4683-9770-3043f1b32ef6', 'Орловская область');
insert into regions (id,name) values ('d3b1ef70-2273-4b61-b8ca-e0a881fade60', 'Пензенская область');
insert into regions (id,name) values ('61b8fb4e-f449-4be8-95a2-2f2fabf50cf5', 'Пермский край');
insert into regions (id,name) values ('c08037cd-9bdd-432a-8dfa-2e821cdfaf56', 'Приморский край');
insert into regions (id,name) values ('4cfd85b0-2596-480c-9f73-2c4c902e3cc4', 'Псковская область');
insert into regions (id,name) values ('20df6707-20b1-46bb-9179-341147b709fd', 'Республика Адыгея');
insert into regions (id,name) values ('88534bc7-e790-460c-8474-07e2a82877a1', 'Республика Алтай');

insert into regions (id,name) values ('61402b40-b269-4aaf-8b37-6c1941ab2b8d', 'Республика Башкортостан');
insert into regions (id,name) values ('7964ceca-d0d9-4f59-9d4b-7e01bdd88b42', 'Республика Бурятия');
insert into regions (id,name) values ('db74c604-2c2e-4ff7-b3b5-a9b8f54787de', 'Республика Дагестан');
insert into regions (id,name) values ('e62c613d-40f9-428d-b395-ddbdf4116b06', 'Республика Ингушетия');
insert into regions (id,name) values ('d1bcc489-e5e3-443f-9404-25886b792433', 'Республика Калмыкия');
insert into regions (id,name) values ('acc7a446-ea3c-47e1-bb06-e80ded34063f', 'Республика Карелия');
insert into regions (id,name) values ('4ec80605-135c-40d7-8fc8-fffb19e2e1bd', 'Республика Коми');
insert into regions (id,name) values ('ca731ea3-b0dc-4252-8f7a-9f87d635b1ad', 'Республика Крым');
insert into regions (id,name) values ('550baecc-ec8a-4094-8b91-101d66f28388', 'Республика Марий Эл');
insert into regions (id,name) values ('880ced76-d184-4fd3-9df4-83ac45e9fcd2', 'Республика Мордовия');

insert into regions (id,name) values ('fce9c744-a40b-49f1-9c70-c97aafd45fb7', 'Республика Саха (Якутия)');
insert into regions (id,name) values ('d0b47d9e-6513-4892-93eb-f8700da80033', 'Республика Северная Осетия');
insert into regions (id,name) values ('ce6921aa-6182-4b96-bef0-17750f7a1dd0', 'Республика Татарстан');
insert into regions (id,name) values ('245184ae-04c2-4748-a845-305f933f824b', 'Республика Тыва');
insert into regions (id,name) values ('ff366706-679d-42a9-b5c4-84fc52dd6734', 'Республика Хакасия');
insert into regions (id,name) values ('3326cc40-5f8b-4980-9509-47cf2e37ed97', 'Ростовская область');
insert into regions (id,name) values ('16511ead-a5b6-47e5-bc28-899552c324ee', 'Рязанская область');
insert into regions (id,name) values ('df9f5ccc-d276-4756-a49a-e06ac9c57ba0', 'Самарская область');
insert into regions (id,name) values ('e06e267c-e55a-41d3-b8ed-410768c4f105', 'Саратовская область');
insert into regions (id,name) values ('5913b07a-5192-4616-a82b-a3d2332ffbfc', 'Сахалинская область');

insert into regions (id,name) values ('daf49f04-82fc-4d70-b7d7-f22281917b70', 'Свердловская область');
insert into regions (id,name) values ('e853c762-defd-4390-8494-b1482425e6a7', 'Смоленская область');
insert into regions (id,name) values ('e35a9def-d18e-42e7-a2ed-77cbcd2e12fa', 'Ставропольский край');
insert into regions (id,name) values ('64cfee6d-b957-449c-8ef8-de5153571cf9', 'Тамбовская область');
insert into regions (id,name) values ('68e174eb-4661-4439-a7da-3dd8462789a8', 'Тверская область');
insert into regions (id,name) values ('f77746fc-83d0-4ddf-a1b0-c4bd33a9c953', 'Томская область');
insert into regions (id,name) values ('9cfd3f5a-2210-4a3b-8f20-557b3c4aa323', 'Тульская область');
insert into regions (id,name) values ('f4ed05c6-6681-405c-b6a2-4fe0d26851cd', 'Тюменская область');
insert into regions (id,name) values ('c3795847-9214-4994-90aa-77d5e129db4b', 'Удмуртская Республика');
insert into regions (id,name) values ('e696f45f-5cfd-42cb-a822-3ab38760510f', 'Ульяновская область');

insert into regions (id,name) values ('1298deb3-3fea-428d-97bc-3aeb1e784053', 'Хабаровский край');
insert into regions (id,name) values ('d125ae6d-6b8a-419c-bf20-0d488869e609', 'Ханты-Мансийский автономный округ');
insert into regions (id,name) values ('d80dc9b7-317b-43a3-a4b8-70bfcdc22761', 'Херсонская область');
insert into regions (id,name) values ('aeb5a661-0e4c-41fd-a8a7-0075c6ca9e5f', 'Челябинская область');
insert into regions (id,name) values ('3b691972-72d1-4726-a3cc-661337d587d5', 'Чеченская Республика');
insert into regions (id,name) values ('e0d0089a-9c5f-495c-977d-e3ebef4855bf', 'Чувашская Республика');
insert into regions (id,name) values ('9192d844-ac83-4d67-a137-e2873ea04d1e', 'Чукотский автономный округ');
insert into regions (id,name) values ('2d244195-29c6-40d1-a26f-a23efe5547c6', 'Ямало-Ненецкий автономный округ');
insert into regions (id,name) values ('4359ff82-b9bf-4d33-b58f-c763120d808b', 'Ярославская область');

-- Создание спортсмена
insert into users (id, email, password, role, status, name, surname, patronymic, birth_date)
values ('fd4ccbdf-71cf-4f58-ab95-c01884f5d19c', 'sportsman@mail.ru', '$2a$12$FbG/RhA5yloQfG7vKFKo8.EJQd8Ob0wiHdmVMPNDnB4ZVk0cDF7xy', 'SPORTSMAN', 'ACTIVE', 'sportsman', 'sportsman', 'sportsman', '2003-01-20');
insert into sportsmen(id, sex_id, sports_title_id, region_id)
values ('fd4ccbdf-71cf-4f58-ab95-c01884f5d19c', 'c99ccd51-5731-42a3-9cfc-31cc4011e035', '6ac053fd-8568-4a36-ae7c-741980d72684', 'b14c7476-fdb9-4d3d-9338-33c846b1042f')
