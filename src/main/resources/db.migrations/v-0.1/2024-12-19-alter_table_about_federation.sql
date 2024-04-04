-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table about_federation
        rename linkforregulation  to link_for_regulation;

alter table about_federation
    rename column linkforhistory to link_for_history;
