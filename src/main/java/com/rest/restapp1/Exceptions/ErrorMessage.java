package com.rest.restapp1.Exceptions;

public class ErrorMessage{

    private int status;
    private String message;
    private long timeStamp;

    public ErrorMessage(int status, String message, long timeStamp){
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return timeStamp;
    }

    public void setTimestamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ErrorMessage(){

    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

}
