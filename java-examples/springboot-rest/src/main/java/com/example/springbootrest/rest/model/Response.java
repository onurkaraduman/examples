package com.example.springbootrest.rest.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T extends RestModel> extends ResponseEntity<T> {

    public Response() {
        super(HttpStatus.OK);
    }

    public Response(T body, HttpStatus status) {
        super(body, status);
    }

    public Response(T body) {
        super(body, HttpStatus.OK);
    }
}