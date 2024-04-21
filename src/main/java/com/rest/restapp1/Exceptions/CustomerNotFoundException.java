package com.rest.restapp1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }

    @ExceptionHandler
    //How do I use this? Can we work one out?
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }

}
