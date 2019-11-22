package com.onrkrdmn.springbootcertauth.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public <T> ResponseEntity<T> response(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<String> success() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
