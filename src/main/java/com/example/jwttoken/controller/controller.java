package com.example.jwttoken.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.service.naverLoingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class controller {

    @Autowired
    private naverLoingService naverLoingService;
    
    @RequestMapping("/auth/auth")
    public String auth(HttpServletRequest request,HttpServletResponse response,Model model) {
        System.out.println("auth/auth");

        return "authpage";
    }
    @GetMapping("/auth2")
    public String auth2(HttpServletRequest request,@AuthenticationPrincipal principaldetail principaldetail) {
        System.out.println("입장");
        System.out.println(request.getHeader("Authorization")+principaldetail.getUsername()+"입장");
        return "auth2page";
    }
    
    
    @RequestMapping("/auth/home4")
    public String name(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("home4");
        System.out.println(request.getParameter("test2"));
   
     

        return "auth2page";
    }
 
}
