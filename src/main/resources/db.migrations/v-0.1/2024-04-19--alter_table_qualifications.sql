-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table qualifications
    add sports_title_id varchar(36);