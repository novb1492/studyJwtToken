package com.example.jwttoken.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.userDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class jwtLoginFilter extends UsernamePasswordAuthenticationFilter  {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    public jwtLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    //login 요청시 얘가 로그인을 진행해준다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {
        System.out.println("로그인요청 attemptAuthentication  ");
  
        //email,pwd로그인 시도
        try {
           /* BufferedReader bufferedReader=request.getReader();
            String input=null;
            while((input=bufferedReader.readLine())!=null){
                System.out.println(input);
            }*/// 원시적인 방법 json(x)
            ObjectMapper objectMapper=new ObjectMapper();
            userDto userDto=objectMapper.readValue(request.getInputStream(), userDto.class);
            System.out.println(userDto);
            
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPwd());
            Authentication authentication=authenticationManager.authenticate(authenticationToken);////토큰을 던지면 loadUserByUsername가 실행됨

            principaldetail principaldetail=(principaldetail)authentication.getPrincipal();
            System.out.println("로그인완료"+principaldetail.getUserDto().getEmail());

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException {
                System.out.println("토큰 제작시작");

                principaldetail principaldetail=(principaldetail)authResult.getPrincipal();
                
                String jwtToken=JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id",principaldetail.getUserDto().getId()).withClaim("username", principaldetail.getUserDto().getEmail()).sign(Algorithm.HMAC512("cos"));
        
                response.setHeader("Authorization", "Bearer "+jwtToken);

                RequestDispatcher dispatcher=request.getRequestDispatcher("/auth/home4"); 
        
                try {
                    dispatcher.forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    
}
