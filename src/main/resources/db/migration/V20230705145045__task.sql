CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username TEXT not null ,
    password TEXT not null
);

CREATE INDEX user_username_idx ON users(username);

CREATE TABLE chat_room
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT
);
CREATE INDEX chat_room_name_idx ON chat_room(name);

CREATE TABLE user_chat_room
(
    user_id      BIGSERIAL,
    chat_room_id BIGSERIAL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id)
);

CREATE TABLE message
(
    id           BIGSERIAL PRIMARY KEY,
    chat_room_id BIGSERIAL,
    sender_id    BIGSERIAL,
    recipient_id BIGSERIAL,
    content      TEXT,
    datetime     TIMESTAMP,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id),
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (recipient_id) REFERENCES users (id)
);
