alter table activity_federation
    rename column file_name to file_names_3d;

alter table activity_federation
    add column file_names_classic varchar(500);

alter table activity_federation
    add column file_names_Biathlon varchar (500);
