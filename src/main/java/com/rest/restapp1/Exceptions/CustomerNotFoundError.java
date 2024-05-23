package com.rest.restapp1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class CustomerNotFoundError {


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundError(CustomerNotFoundException exception) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedTimestamp = now.format(formatter);
        error.setTimestamp(formattedTimestamp);

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }

}
