alter table articles
    alter column date type TIMESTAMP using date::TIMESTAMP;

alter table articles
    alter column date set not null;
