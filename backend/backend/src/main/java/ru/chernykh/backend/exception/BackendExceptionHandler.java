package ru.chernykh.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class BackendExceptionHandler {
    @ExceptionHandler({
            AuthenticationException.class, UserCollisionException.class, AuthorizationException.class})
    public ResponseEntity<UserCollisionException> handelUserCollisionException(UserCollisionException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void as(Exception ex) throws Exception {
        ex.printStackTrace();
        throw ex;
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> noSuchEl(Exception e) {
        return ResponseEntity.badRequest().body(e);
    }
}
