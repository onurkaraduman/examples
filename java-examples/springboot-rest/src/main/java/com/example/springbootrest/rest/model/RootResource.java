package com.example.springbootrest.rest.model;

import org.springframework.hateoas.ResourceSupport;

public class RootResource extends ResourceSupport {
    private String message;

    public RootResource() {
        this.message = "Welcome to springboot-rest api version 1.0.0";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
