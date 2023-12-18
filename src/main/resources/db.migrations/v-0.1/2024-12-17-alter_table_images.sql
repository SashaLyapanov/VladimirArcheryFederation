-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table articles
    add link varchar(256);

alter table articles
    drop column preview_image_id;

drop table images;