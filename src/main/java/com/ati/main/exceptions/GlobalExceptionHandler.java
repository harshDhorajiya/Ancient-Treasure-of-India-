package com.ati.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <Map<String,String>> handlemethodnotvalidexception (MethodArgumentNotValidException ex){

        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((er) -> {
            String field = ((FieldError)er).getField();
            String message = er.getDefaultMessage();
            resp.put(field,message);
        });
       return  new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }

}
