package com.example.jwttoken.controller;








import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.example.jwttoken.model.testDTO;
import com.example.jwttoken.service.naverLoingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {

    @Autowired
    private naverLoingService naverLoingService;



    @PostMapping("/auth/naver")
    public String naverLogin() {
        return  naverLoingService.naverLogin();
    }
    @RequestMapping("/auth/jsontest")
    public String authjsontest(@RequestBody testDTO dto) {
        System.out.println("jsontest입장");
        System.out.println(dto.getEmail());
       /*ObjectMapper objectMapper=new ObjectMapper();
        try {
            testDTO userDto = objectMapper.readValue(request.getInputStream(), testDTO.class);
            System.out.println(userDto);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return "auth/jsontest";
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
        System.out.println(request.getHeader("Authorization"));
        return "/auth/head";
    }
    @RequestMapping("/auth/listtest")
    public String  listtest(@RequestParam("list[]")List<String>a,HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/auth/listtest 입장");
        System.out.println(request.getParameter("test"));
        System.out.println(a.get(0));
        return "listtest";
    }
    @RequestMapping("/api/v1/user/test")
    public String  usertest() {
        return "user";
    }
    @RequestMapping("/api/v1/manage/test")
    public String  mangetest() {
        return "mange";
    }
    @RequestMapping("/api/v1/admin/test")
    public String  admintest() {
        return "admin";
    }
   
}
