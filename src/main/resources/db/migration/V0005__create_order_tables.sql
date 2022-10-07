CREATE TABLE "order_header" (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  creation_date timestamp NOT NULL
);

CREATE TABLE "order_detail" (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  creation_date timestamp NOT NULL,
  "order" SERIAL NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  fullname varchar(200) NOT NULL,
  email varchar(100) NOT NULL,
  "address" varchar(200) NOT NULL,
  idNumber varchar(100) NOT NULL,
  FOREIGN KEY ("order") REFERENCES "order_header"(id)
);

CREATE TABLE "order_product" (
  id SERIAL UNIQUE PRIMARY KEY NOT NULL,
  creation_date timestamp NOT NULL,
  "order" SERIAL NOT NULL,
  product SERIAL NOT NULL,
  quantity SMALLINT NOT NUll,
  FOREIGN KEY ("order") REFERENCES "order_header"(id),
  FOREIGN KEY ("product") REFERENCES "product"(id)
);