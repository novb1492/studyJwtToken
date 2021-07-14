package com.example.jwttoken.controller;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.testDTO;
import com.example.jwttoken.service.naverLoingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {

    @Autowired
    private naverLoingService naverLoingService;

    @GetMapping("/home")
    public String name(@AuthenticationPrincipal principaldetail principaldetail) {
        return principaldetail.getUsername();
    }
    @ModelAttribute
    @RequestMapping("/auth/home")
    public void auth(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth/home");
        request.setAttribute("test", "test");
        String tt="tt";
        
        RequestDispatcher dispatcher=request.getRequestDispatcher("/auth/home4"); 
        
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    @RequestMapping("/auth/home2")
    public void auth2(HttpServletRequest request,HttpServletResponse response) {
       
        System.out.println("/auth/home2");
        System.out.println(request.getParameter("test2"));
        System.out.println(request.getHeader("test"));
       
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
    @GetMapping("/auth/navercallback")
    public void naverLogin2(@RequestParam("code")String code, @RequestParam("state") String state,HttpServletRequest request,HttpServletResponse response) {
        System.out.println("naverlogin요청");
     naverLoingService.LoginNaver(naverLoingService.getNaverToken(code, state),request,response);
      
    
    }
   
}
