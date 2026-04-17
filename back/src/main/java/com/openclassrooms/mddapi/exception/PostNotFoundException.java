package com.openclassrooms.mddapi.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post not found: " + id);
    }
}
