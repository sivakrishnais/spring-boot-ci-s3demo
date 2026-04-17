package com.codesnippet.S3DemoApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/test1")
    public List<String> test1() {
        return List.of("Siva", "Krishna", "Reddy");
    }

}
