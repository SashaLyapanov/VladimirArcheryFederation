-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table protocols
    add column qualification bool DEFAULT FALSE;