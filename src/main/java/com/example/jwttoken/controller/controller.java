package com.example.jwttoken.controller;

import javax.servlet.http.HttpServletResponse;

import com.example.jwttoken.service.naverLoingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class controller {

    @Autowired
    private naverLoingService naverLoingService;
    
    @GetMapping("/auth/auth")
    public String auth() {
        return "authpage";
    }
    @GetMapping("/auth2")
    public String auth2() {
        return "auth2page";
    }
    @GetMapping("/auth/navercallback")
    public String naverLogin2(@RequestParam("code")String code, @RequestParam("state") String state,HttpServletResponse response) {
        System.out.println("naverlogin요청");
        naverLoingService.LoginNaver(naverLoingService.getNaverToken(code, state),response);
        return "auth2page";
    }
}
