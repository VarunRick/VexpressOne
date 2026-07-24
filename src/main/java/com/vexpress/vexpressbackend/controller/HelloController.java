package com.vexpress.vexpressbackend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Vexpress Controller!";
    }

    @GetMapping("/hello/{name}")
    public String wish(@PathVariable String name) {
        return "Hello, " + name + "!, Welcome to Vexpress!";
    }

}
