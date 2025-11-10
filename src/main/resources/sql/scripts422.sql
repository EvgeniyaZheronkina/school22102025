CREATE TABLE car(
id serial PRIMARY KEY,
model text NOT NULL,
brand text NOT NULL,
cost integer check (cost > 0)
)

insert into car (id, brand, model, cost) values (1, 'BMW', 'ggg', 500000);
insert into car (id, brand, model, cost) values (2, 'LADA', 'ddd', 150000);


CREATE TABLE people(
id serial PRIMARY KEY,
name NOT NULL,
age NOT NULL,
license BOOLEAN,
id_car integer REFERENCES cars (id)
)

insert into people (id, name, age, license, id_car) values (1, 'Ivan', 25, 'no');
insert into people (id, name, age, license, id_car) values (2, 'Maria', 32, 'yes', 2);
insert into people (id, name, age, license, id_car) values (2, 'Vladimir', 19, 'yes', 1);

SELECT people.name, people.age, people.license, car.brand, car.model
FROM people INNER JOIN car ON people.id_car = car.id;
