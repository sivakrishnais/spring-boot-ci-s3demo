package com.codesnippet.S3DemoApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World siva krishna welocome";
    }

    @GetMapping("/test1")
    public List<String> tes1() {
        return List.of("Siva", "Krishna", "Reddy");

    }

}
