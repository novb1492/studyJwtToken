package com.example.jwttoken.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.testDTO;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/auth3")
    public String auth3(@RequestBody testDTO test) {
      
    System.out.println(test+"auth3");
       return test.getTest();
    }
}
