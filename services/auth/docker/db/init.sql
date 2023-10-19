CREATE TABLE IF NOT EXISTS rksp_users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(256),
    password VARCHAR(256),
    user_role VARCHAR(256),
    client_id VARCHAR(256) NULL,
    client_name VARCHAR(256) NULL
);