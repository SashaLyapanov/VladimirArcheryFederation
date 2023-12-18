-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table images
    rename column contenttype to content_type;

alter table images
    rename column originalfilename to original_file_name;

alter table images
    rename column ispreviewimage to is_preview_image;