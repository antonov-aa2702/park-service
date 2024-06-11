CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(32)  NOT NULL,
    role     VARCHAR(16)  NOT NULL,
    login    VARCHAR(32)  NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    year INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS trip
(
    id         SERIAL PRIMARY KEY,
    user_id    INT  NOT NULL REFERENCES users (id),
    vehicle_id INT  NOT NULL REFERENCES vehicle (id),
    distance   INT  NOT NULL,
    from_date  DATE NOT NULL,
    to_date    DATE NOT NULL
);

INSERT INTO users(name, role, login, password)
VALUES ('admin', 'ROLE_ADMIN', 'admin', '$2a$12$VXCum1z6sR6jr50j3.7S6OF.GTL1b12Ilv5y/axZJe0xXtvgBWNpS');