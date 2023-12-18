-- liquibase formatted sql

-- changeset lyapanov-s:1
create table images (
    id varchar(36),
    name varchar(256),
    originalFileName varchar(256),
    size bigint,
    contentType varchar(36),
    isPreviewImage bool,
    bytes bytea,
    article_id varchar(36),
    FOREIGN KEY (article_id) references articles(id)
)
