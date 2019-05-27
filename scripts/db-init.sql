CREATE TABLE customers (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(50) NOT NULL,
  surname         VARCHAR(50) NOT NULL,
  email           VARCHAR(50) NOT NULL,
  address         VARCHAR(100) NULL,
  gender          VARCHAR(1) NULL,
  occupation      VARCHAR(50) NULL
);

INSERT INTO customers (name, surname, email, address, gender, occupation) VALUES ('John', 'Smith', 'joe.smith@local.domain', 'That Street, 7', 'M', 'Driver');
INSERT INTO customers (name, surname, email, address, gender, occupation) VALUES ('Anne', 'Downey', 'anne.d@local.domain', 'This Street, 1', 'F', NULL);
INSERT INTO customers (name, surname, email, address, gender, occupation) VALUES ('Patrick', 'Mine', 'p-maine@local.domain', NULL, 'M', NULL);