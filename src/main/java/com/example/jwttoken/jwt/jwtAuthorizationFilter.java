package com.example.jwttoken.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.userDao;
import com.example.jwttoken.model.userDto;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class jwtAuthorizationFilter extends BasicAuthenticationFilter {

    private userDao dao;

    public jwtAuthorizationFilter(AuthenticationManager authenticationManager,userDao dao) {
        super(authenticationManager);
        this.dao=dao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws IOException, ServletException {
        System.out.println("doFilterInternal 입장");
        String jwtHeader=request.getHeader("Authorization");
        if(jwtHeader==null||!jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
        }

        String jwtToken=request.getHeader("Authorization").replace("Bearer ", "");
        System.out.println(jwtToken+"토큰받음");

        String username=JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("username").asString();
        System.out.println(username+"토큰해제");

        if(username!=null){
            System.out.println("인증이 요청되는");
            userDto userDto=dao.findByEmail(username);
            principaldetail principaldetail=new principaldetail(userDto);

            System.out.println(userDto.getEmail());
            System.out.println(principaldetail.getAuthorities());
            
            Authentication authentication=new UsernamePasswordAuthenticationToken(principaldetail, null,principaldetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }
    
}
