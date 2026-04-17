package com.openclassrooms.mddapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("email", ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("username", ex.getMessage()));
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleBadCredentials(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Identifiants invalides"));
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTopicNotFound(TopicNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePostNotFound(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Une erreur interne est survenue"));
    }
}
