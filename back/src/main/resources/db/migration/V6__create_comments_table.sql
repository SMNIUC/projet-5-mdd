CREATE TABLE comments (
    id         BIGSERIAL    PRIMARY KEY,
    content    TEXT         NOT NULL,
    author_id  BIGINT       NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    post_id    BIGINT       NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);
