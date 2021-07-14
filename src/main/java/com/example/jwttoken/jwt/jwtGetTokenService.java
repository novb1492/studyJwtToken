package com.example.jwttoken.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwttoken.config.principaldetail;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class jwtGetTokenService {
    
    public String getJwtToken(Authentication authResult) {
        
        System.out.println("토큰 제작시작");
        principaldetail principaldetail=(principaldetail)authResult.getPrincipal();  
        String jwtToken=JWT.create().withSubject("cos").withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
        .withClaim("id",principaldetail.getUserDto().getId()).withClaim("username", principaldetail.getUserDto().getEmail()).sign(Algorithm.HMAC512("cos"));
        
        return jwtToken;
    }
}
