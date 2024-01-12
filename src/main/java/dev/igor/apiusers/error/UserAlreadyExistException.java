package dev.igor.apiusers.error;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        this("User already exists", null);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
