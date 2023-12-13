ALTER TABLE competitions ADD COLUMN id1 varchar(36);

UPDATE competitions SET id1 = id;

ALTER TABLE competitions DROP COLUMN id;

ALTER TABLE competitions RENAME COLUMN id1 TO id;