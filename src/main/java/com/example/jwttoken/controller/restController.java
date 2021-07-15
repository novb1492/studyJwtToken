package com.example.jwttoken.controller;







import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.testDTO;
import com.example.jwttoken.service.naverLoingService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

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
    @RequestMapping("/auth/auth5")
    public String authAuth4(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("auth/auth5 입장");
        System.out.println(request.getParameter("test")+"결과");
        return "result/auth5";
    }   
    @RequestMapping("/jsontest")
    public String jsontest(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("jsontest입장");
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            testDTO userDto = objectMapper.readValue(request.getInputStream(), testDTO.class);
            System.out.println(userDto);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "result/auth5";
    } 
    @RequestMapping("/head")
    public String  Head(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/head 입장");
        System.out.println(request.getHeader("Authorization"));
        return "head";
    }
    @RequestMapping("/auth/head")
    public String  authHead(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth/head 입장");
        return "yh";
    }
    @RequestMapping("/auth/listtest")
    public String  listtest(@RequestParam("list[]")List<String>a,HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth/listtest 입장");
        System.out.println(request.getParameter("test"));
        System.out.println(a.get(0));
        return "listtest";
    }
   
}
