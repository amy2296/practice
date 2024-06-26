package com.Demo.Example.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityException {

    public static ResponseEntity<Object>handleException(Exception ex, HttpStatus status) {

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}