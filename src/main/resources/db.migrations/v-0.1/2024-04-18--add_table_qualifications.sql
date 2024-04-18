-- liquibase formatted sql

-- changeset lyapanov-s:1
create table qualifications (
    id varchar(36),
    sportsman_id varchar(36) NOT NULL,
    competition_id varchar(36) NOT NULL,
    bow_type_id varchar(36) NOT NULL,
    dist1 int NOT NULL,
    dist2 int NOT NULL,
    sum int,
    quantity11 int NOT NULL,
    quantity10 int NOT NULL,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);