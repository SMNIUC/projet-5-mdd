CREATE TABLE posts (
    id         BIGSERIAL    PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    author_id  BIGINT       NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    topic_id   BIGINT       NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);
