package com.example.jwttoken.controller;







import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.testDTO;
import com.example.jwttoken.service.naverLoingService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {

    @Autowired
    private naverLoingService naverLoingService;

    @GetMapping("/home")
    public String name(@AuthenticationPrincipal principaldetail principaldetail) {
        return principaldetail.getUsername();
    }
  
    @RequestMapping("/auth/home")
    public void auth(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth/home"); 
    }
    @RequestMapping("/auth/home2")
    public void auth2(HttpServletRequest request,HttpServletResponse response) {
       
        System.out.println("/auth/home2");
        System.out.println(request.getParameter("test2"));
        System.out.println(request.getHeader("test"));
       
    }
    @GetMapping("/auth3")
    public String auth3(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth3 입장");
       return "asd";
    }
    @PostMapping("/auth/naver")
    public String naverLogin() {
        return  naverLoingService.naverLogin();
    }
    @RequestMapping("/auth/navercallback")
    public void naverLogin2(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("naverlogin요청");
        naverLoingService.LoginNaver(naverLoingService.getNaverToken(request.getParameter("code"), request.getParameter("state")),request,response);
      
    
    }
   
}
