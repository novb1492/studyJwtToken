package com.example.jwttoken.jwt;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.example.jwttoken.config.principaldetail;
import com.example.jwttoken.model.userDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



public class jwtLoginFilter extends UsernamePasswordAuthenticationFilter  {
    

   

    private jwtGetTokenService getTokenService;

    public jwtLoginFilter(jwtGetTokenService getTokenService){
        this.getTokenService=getTokenService;
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
            
            Authentication authentication=getTokenService.getAuthentication(userDto);
            
            System.out.println("로그인완료"+authentication.getName());

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
                String jwtToken=getTokenService.getJwtToken(principaldetail.getUserDto().getEmail());
                String refeshToken=getTokenService.getRefreshToken();
                
                getTokenService.insertRefreshToken("Bearer "+refeshToken,principaldetail.getUserDto().getEmail());
                response.setHeader("Authorization", "Bearer "+jwtToken);
                response.setHeader("refreshToken","Bearer "+refeshToken);
                
                RequestDispatcher dispatcher=request.getRequestDispatcher("/auth/auth"); 
                try {

                    dispatcher.forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }          
    }
    
}
