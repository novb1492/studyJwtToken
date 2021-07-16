package com.example.jwttoken.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.userDto;
import com.example.jwttoken.model.jwt.jwtDao;
import com.example.jwttoken.model.jwt.jwtDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class jwtGetTokenService {

    @Autowired
    private jwtDao dao;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public String getJwtToken(String email) {
        
        System.out.println("토큰 제작시작");
       
        return JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+1000))
        .withClaim("username",email).sign(Algorithm.HMAC512("cos"));
    }
    public void insertRefreshToken(String tokenName,String email) {
        try {
            jwtDto dto=new jwtDto();
            dto.setTokenName(tokenName);
            dto.setEmail(email);
            dao.save(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public jwtDto findTokenAtDb(String tokenName) {
        try {
            jwtDto dto=dao.findByTokenName(tokenName);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("토큰이 존재하지 않습니다");
        return null;
    }
    public String getRefreshToken() { 
        return JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+(60000*1000))).sign(Algorithm.HMAC512("cos"));   
    }
    public String getRefreshToken(String token) {
        try {
            jwtDto dto=dao.findByTokenName(token);
            return dto.getEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("리프레시 토큰이 존재하지 않습니다");
        return null;
    }
    public Authentication getAuthentication(userDto dto) {
        principaldetail principaldetail=new principaldetail(dto);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), "1111",principaldetail.getAuthorities()));
    }
}
