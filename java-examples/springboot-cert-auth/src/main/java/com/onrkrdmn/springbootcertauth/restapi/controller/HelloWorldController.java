package com.onrkrdmn.springbootcertauth.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloWorldController extends BaseController {

    @RequestMapping("hello")
    public ResponseEntity<String> helloWorld() {
        return response("Hello World");
    }
}
