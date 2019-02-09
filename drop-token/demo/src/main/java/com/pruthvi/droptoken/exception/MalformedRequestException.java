package com.pruthvi.droptoken.exception;

public class MalformedRequestException extends Exception {

    private String resourceId;

    public MalformedRequestException(String resourceId,String message){
        super(message);
        this.resourceId = resourceId;
    }

    public MalformedRequestException(String message) {
        super(message);
    }
}
