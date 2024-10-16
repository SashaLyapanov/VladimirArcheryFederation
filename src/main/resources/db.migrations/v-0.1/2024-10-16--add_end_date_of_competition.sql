-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table competitions
    add competition_date_end date;