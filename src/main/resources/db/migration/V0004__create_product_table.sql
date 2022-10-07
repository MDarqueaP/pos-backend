CREATE TABLE product (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  "name" VARCHAR(40) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  "description" VARCHAR(250) NOT NUll,
  use_cases TEXT NOT NUll,
  format_available TEXT NOT NUll,
  stock SMALLINT DEFAULT 0,
  creation_date timestamp NOT NULL,
  update_date timestamp
);