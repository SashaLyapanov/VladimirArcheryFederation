-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table qualifications
    add column place int;