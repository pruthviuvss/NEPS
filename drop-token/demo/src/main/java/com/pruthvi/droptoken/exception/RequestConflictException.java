package com.pruthvi.droptoken.exception;

public class RequestConflictException extends Exception{

    private String resourceId;

    public RequestConflictException(String resourceId,String message){
        super(message);
        this.resourceId = resourceId;
    }

    public RequestConflictException(String message) {
        super(message);
    }
}
