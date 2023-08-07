
CREATE TABLE book
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    isbn       VARCHAR(20)  NOT NULL UNIQUE,
    author     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE collection
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE book_collection
(
    book_id       BIGINT REFERENCES book (id) ON DELETE CASCADE,
    collection_id BIGINT REFERENCES collection (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, collection_id)
);
