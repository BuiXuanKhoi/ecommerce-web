package com.example.ecommerce_web.exceptions.handler;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.ErrorRespond;
import com.fasterxml.jackson.databind.deser.impl.ErrorThrowingDeserializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.NOT_FOUND.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex){
        String message = "Enum constant not exists ";

        int statusCode =  HttpStatus.BAD_REQUEST.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode,message);

        return new ResponseEntity<>(errorRespond, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ConstraintViolateException.class})
    public ResponseEntity<?> handleConstraintViolateException(ConstraintViolateException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);
        return new ResponseEntity<>( errorRespond, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.BAD_REQUEST);
    }


}
