CREATE TABLE users_rols
(
    user_id      BIGSERIAL,
    role_id BIGSERIAL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);