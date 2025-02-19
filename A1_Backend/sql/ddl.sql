DROP TABLE IF EXISTS t_users, rooms;

CREATE TABLE t_users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE rooms
(
    id           BIGSERIAL PRIMARY KEY,
    room_name    VARCHAR(255) NOT NULL,
    department   VARCHAR(255),
    room_type    VARCHAR(50),
    location     VARCHAR(255),
    date         DATE,
    start_time   TIME,
    end_time     TIME,
    max_duration VARCHAR(50)
);
