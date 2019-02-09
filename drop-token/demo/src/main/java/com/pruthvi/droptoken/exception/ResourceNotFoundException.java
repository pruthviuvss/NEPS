package com.pruthvi.droptoken.exception;

public class ResourceNotFoundException extends Exception {

    private String resourceId;

    public ResourceNotFoundException(String resourceId,String message){
        super(message);
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
