package com.example.jwttoken.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.jwt.jwtDao;
import com.example.jwttoken.model.jwt.jwtDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class jwtGetTokenService {

    @Autowired
    private jwtDao dao;
    
    public String getJwtToken(principaldetail principaldetail) {
        
        System.out.println("토큰 제작시작");
       
        return JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
        .withClaim("id",principaldetail.getUserDto().getId()).withClaim("username", principaldetail.getUserDto().getEmail()).sign(Algorithm.HMAC512("cos"));
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
        return JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))).sign(Algorithm.HMAC512("cos"));   
    }
}
