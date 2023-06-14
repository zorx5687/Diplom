package ru.chernykh.backend.exception;

public class UserCollisionException extends RuntimeException {
    public UserCollisionException(String message) {
        super(message);
    }
}
