package com.rest.restapp1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerNotFoundException {


    @ExceptionHandler
    //How do I use this? Can we work one out?
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(CustomerExists exception) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }

}
