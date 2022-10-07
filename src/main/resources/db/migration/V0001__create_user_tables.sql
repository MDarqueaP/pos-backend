CREATE TABLE "user" (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  first_name varchar(100) NOT NULL,
  last_name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  active boolean default true,
  "password" varchar(100) NOT NULL,
  creation_date timestamp NOT NULL,
  update_date timestamp
);

CREATE TABLE "role" (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  "name" VARCHAR(40) NOT NULL
);

CREATE TABLE user_role (
  id varchar(40) UNIQUE PRIMARY KEY NOT NULL,
  "user" SERIAL NOT NULL,
  "role" SERIAL NOT NULL,
  FOREIGN KEY ("user") REFERENCES "user"(id),
  FOREIGN KEY ("role") REFERENCES "role"(id)
);