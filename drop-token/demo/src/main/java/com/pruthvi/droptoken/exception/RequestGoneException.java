package com.pruthvi.droptoken.exception;

public class RequestGoneException extends Exception {

    private String resourceId;


    public RequestGoneException(String resourceId,String message){
        super(message);
        this.resourceId = resourceId;
    }

    public RequestGoneException(String message) {
        super(message);
    }
}
