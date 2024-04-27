-- liquibase formatted sql

-- changeset lyapanov-s:1
create table protocol_stage2
(
    id varchar(36),
    sportsman_id varchar(36) NOT NULL,
    competition_id varchar(36) NOT NULL,
    bow_type_id varchar(36) NOT NULL,
    qualification_result int NOT NULL,
    result_of_this_stage int NOT NULL,
    place int,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

create table protocol_stage4
(
    id varchar(36),
    sportsman_id varchar(36) NOT NULL,
    competition_id varchar(36) NOT NULL,
    bow_type_id varchar(36) NOT NULL,
    qualification_result int NOT NULL,
    result_of_this_stage int NOT NULL,
    place int,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

create table protocol_stage8
(
    id varchar(36),
    sportsman_id varchar(36) NOT NULL,
    competition_id varchar(36) NOT NULL,
    bow_type_id varchar(36) NOT NULL,
    qualification_result int NOT NULL,
    result_of_this_stage int NOT NULL,
    place int,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);

create table protocol_final
(
    id varchar(36),
    sportsman_id varchar(36) NOT NULL,
    competition_id varchar(36) NOT NULL,
    bow_type_id varchar(36) NOT NULL,
    qualification_result int NOT NULL,
    result_of_stage2 int NOT NULL,
    result_of_this_stage int NOT NULL,
    place int,
    FOREIGN KEY (sportsman_id) REFERENCES sportsmen (id),
    FOREIGN KEY (competition_id) REFERENCES competitions (id),
    FOREIGN KEY (bow_type_id) REFERENCES bow_types (id)
);