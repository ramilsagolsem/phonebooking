drop table users cascade constraints;
drop table mobile cascade constraints;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS mobile (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  technology VARCHAR(255),
  availability VARCHAR(255),
  bookedon VARCHAR(255),
  bookedby INT,
  FOREIGN KEY (bookedby) REFERENCES users(id)
);

INSERT INTO Users (id, name)
SELECT 1, 'John Snow'
where not exists (select id from users where id=1);

INSERT INTO Users (id, name)
SELECT 2, 'James Doe'
where not exists (select id from users where id=2);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 1, 'Samsung Galaxy S9', null, 'Yes'
where not exists (select id from mobile where id=1);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 2, 'Samsung Galaxy S8', null, 'Yes'
where not exists (select id from mobile where id=2);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 3, 'Samsung Galaxy S8', null, 'Yes'
where not exists (select id from mobile where id=3);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 4, 'Motorola Nexus 6', null, 'Yes'
where not exists (select id from mobile where id=4);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 5, 'Oneplus 9', null, 'Yes'
where not exists (select id from mobile where id=5);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 6, 'iPhone 13', null, 'Yes'
where not exists (select id from mobile where id=6);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 7, 'iPhone 12', null, 'Yes'
where not exists (select id from mobile where id=7);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 8, 'iPhone 11', null, 'Yes'
where not exists (select id from mobile where id=8);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 9, 'iPhone X', null, 'Yes'
where not exists (select id from mobile where id=9);

INSERT INTO Mobile (id, name, technology, availability)
SELECT 10, 'Nokia 3310', null, 'Yes'
where not exists (select id from mobile where id=10);

