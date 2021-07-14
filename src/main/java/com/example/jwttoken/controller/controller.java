package com.example.jwttoken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {
    @GetMapping("/auth/auth")
    public String auth() {
        return "authpage";
    }
    @GetMapping("/auth2")
    public String auth2() {
        return "auth2page";
    }
}
