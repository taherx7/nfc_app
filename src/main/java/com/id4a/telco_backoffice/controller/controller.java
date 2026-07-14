package com.id4a.telco_backoffice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @GetMapping("/")
    public String home() {
        return "Backend is connected to MySQL and running smoothly!";
    }
}