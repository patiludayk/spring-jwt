package com.uday.spring.springjwt.config.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointer(NullPointerException ex, WebRequest request) {
        String bodyOfResponse = "value should not be null.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    protected ResponseEntity<Object> handleInternalServerError(HttpServerErrorException.InternalServerError ex, WebRequest request) {
        return new ResponseEntity<Object>( "Something went wrong.", new HttpHeaders(), HttpStatus.OK);
    }

}
