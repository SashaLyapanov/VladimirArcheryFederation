alter table articles
    alter column name type varchar(300) using name::varchar(300);

insert into sports_titles (id, sports_title_name) values ('dd015fc2-da4f-4e6e-a7f8-5158a84d7fab', 'б.р.');
