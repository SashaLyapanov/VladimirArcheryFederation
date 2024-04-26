-- liquibase formatted sql

-- changeset lyapanov-s:1
alter table protocols
    add column block_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column block_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column classic_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column classic_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column long_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column long_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column composite_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column composite_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column sporting_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column sporting_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column history_bow_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column olympic_3d_woman_final bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_man_final bool DEFAULT FALSE;
alter table protocols
    add column arbalet_3d_woman_final bool DEFAULT FALSE;