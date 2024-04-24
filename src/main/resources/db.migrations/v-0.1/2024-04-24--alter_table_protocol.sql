-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table protocols
    add column history_bow_3d_man8 bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_woman8 bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_man4 bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_woman4 bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_man2 bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_woman2 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_man8 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_woman8 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_man4 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_woman4 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_man2 bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_woman2 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_man8 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_woman8 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_man4 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_woman4 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_man2 bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_woman2 bool DEFAULT FALSE;