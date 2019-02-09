package com.pruthvi.droptoken.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ExceptionResponse> genericException(){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Unknown exception");
        exceptionResponse.setErrorMessage("Please check the request for all the possible errors.");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> missingParameter(MethodArgumentNotValidException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("All parameters not found");
        exceptionResponse.setErrorMessage("One of the parameters in the post request is not entered.");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> missingParameter(HttpRequestMethodNotSupportedException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Wrong request.");
        exceptionResponse.setErrorMessage("This request is not supported by the API");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> missingParameter(HttpMessageNotReadableException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Wrong request.");
        exceptionResponse.setErrorMessage("This request cannot be served by the API.");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex ){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Resource Not found");
        exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestConflictException.class)
    public ResponseEntity<ExceptionResponse> requestConflictException(RequestConflictException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Request Conflict");
        exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MalformedRequestException.class)
    public ResponseEntity<ExceptionResponse> requestConflictException(MalformedRequestException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Malformed Request");
        exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestGoneException.class)
    public ResponseEntity<ExceptionResponse> requestGoneException(RequestGoneException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("Request Gone");
        exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.GONE);
    }
}
