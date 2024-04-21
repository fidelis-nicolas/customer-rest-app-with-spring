package com.rest.restapp1.Exceptions;

public class CustomerExists extends RuntimeException{
    public CustomerExists(String message){
        super(message);
    }
}
