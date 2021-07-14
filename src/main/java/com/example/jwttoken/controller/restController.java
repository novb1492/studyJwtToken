package com.example.jwttoken.controller;



import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.testDTO;
import com.example.jwttoken.service.naverLoingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {

    @Autowired
    private naverLoingService naverLoingService;

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
    @PostMapping("/auth/naver")
    public String naverLogin() {
        return  naverLoingService.naverLogin();
    }
   
}
