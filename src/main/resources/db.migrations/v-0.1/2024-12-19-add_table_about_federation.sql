-- liquibase formatted sql

-- changeset lyapanov-s:1
create table about_federation (
    id varchar(36),
    managers varchar(300),
    contacts varchar(256),
    linkForRegulation varchar(256),
    linkForHistory varchar(256)
);