package dev.igor.apiusers.error.handler;

import dev.igor.apiusers.error.UserAlreadyExistException;
import dev.igor.apiusers.error.UserNotFoundException;
import dev.igor.apiusers.error.response.Error;
import dev.igor.apiusers.error.response.ResponseError;
import dev.igor.apiusers.error.response.ResponseErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ResponseError> handleUserAlreadyExists(UserAlreadyExistException ex) {
        return ResponseEntity.badRequest().body(
                new ResponseError(
                        HttpStatus.BAD_REQUEST.toString(),
                        ex.getMessage(),
                        UserAlreadyExistException.class.getSimpleName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorList> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(item -> {
            String fieldName = item.getField();
            String errorMessage = item.getDefaultMessage();
            errors.add(new Error(String.format("'%s' - %s", fieldName, errorMessage)));
        });
        return ResponseEntity.badRequest().body(new ResponseErrorList(
                HttpStatus.BAD_REQUEST.toString(),
                MethodArgumentNotValidException.class.getSimpleName(),
                errors
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(
                new ResponseError(
                        HttpStatus.BAD_REQUEST.toString(),
                        ex.getMessage().substring(0, 32),
                        HttpMessageNotReadableException.class.getSimpleName()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
