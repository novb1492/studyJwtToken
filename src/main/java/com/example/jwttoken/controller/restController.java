package com.example.jwttoken.controller;

import com.example.jwttoken.config.principaldetail;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {
    @GetMapping("/home")
    public String name(@AuthenticationPrincipal principaldetail principaldetail) {
        return principaldetail.getUsername();
    }
    @GetMapping("/auth/home")
    public String auth() {
        return "home";
    }
    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }
}
