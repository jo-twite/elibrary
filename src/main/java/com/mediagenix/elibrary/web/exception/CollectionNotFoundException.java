package com.mediagenix.elibrary.web.exception;

public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(Long id) {
        super(String.format("Collection with id=%d not found", id));
    }
}
