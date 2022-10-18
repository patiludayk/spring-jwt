package com.uday.spring.springjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AboutMeController {

    @GetMapping("/")
    public Map<String, String> getApiInfo(){
        Map<String, String> apis = new LinkedHashMap<>();
        apis.put("Home", "/");
        apis.put("About Me", "/aboutme");
        apis.put("Register user", "/register");
        apis.put("Login", "/login");
        return apis;
    }

    @GetMapping("/aboutme")
    public ResponseEntity aboutMe(){
        return ResponseEntity.ok("Hello user, this is about me api");
    }

    @PostMapping("/register")
    public String registerUser(){
        return "user registered.";
    }

    @PostMapping("/login")
    public String login(){
        return "user login";
    }

}
