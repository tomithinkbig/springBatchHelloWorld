-- this is apache Derby syntax
--DROP TABLE people;

CREATE TABLE  people  (
    person_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);