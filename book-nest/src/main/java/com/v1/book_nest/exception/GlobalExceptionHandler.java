package com.v1.book_nest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Handle custom ApplicationException
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
    }

    //Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request){
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Handle User not found exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    //Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex,WebRequest request){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
