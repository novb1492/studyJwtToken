package com.example.jwttoken.service;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.example.jwttoken.config.security;
import com.example.jwttoken.jwt.jwtGetTokenService;
import com.example.jwttoken.model.naverDto;
import com.example.jwttoken.model.userDao;
import com.example.jwttoken.model.userDto;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class naverLoingService   {
    
    private final String id="DrqDuzgTpM_sfreaZMly";
    private final String pwd="wCLQZ1kaQT";
    private final String callBackUrl="http://localhost:8080/auth/navercallback";

    private RestTemplate restTemplate=new RestTemplate();
    private HttpHeaders headers=new HttpHeaders();

    @Autowired
    private userDao dao;
    @Autowired
    private security security;
    @Autowired
    private jwtGetTokenService jwtGetTokenService;
  

    public String naverLogin() {
        String state="";
        try {
            state = URLEncoder.encode(callBackUrl, "UTF-8");
            return "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+id+"&redirect_uri="+""+callBackUrl+""+"&state="+state+"";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            throw new RuntimeException("naverLogin 오류 발생");
        } 
    }
    public JSONObject getNaverToken(String code,String state) {
         JSONObject jsonObject= restTemplate.getForObject("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id="+id+"&client_secret="+pwd+"&code="+code+"&state="+state+"",JSONObject.class);
         System.out.println(jsonObject+" token"); 
         return jsonObject;
     }
     public void LoginNaver(JSONObject jsonObject,HttpServletRequest request,HttpServletResponse response) {
        headers.add("Authorization", "Bearer "+jsonObject.get("access_token"));
        HttpEntity<JSONObject>entity=new HttpEntity<JSONObject>(headers);
        try {
           naverDto naverDto =restTemplate.postForObject("https://openapi.naver.com/v1/nid/me",entity,naverDto.class);
           System.out.println(naverDto+ "정보");
           
           String[] split=naverDto.getResponse().get("email").toString().split("@");
           String email="";

           if(!split[1].equals("naver.com")){
               email=split[0]+"@naver.com";
           }else{
               email=naverDto.getResponse().get("email").toString();
           }
          
               split=naverDto.getResponse().get("mobile_e164").toString().split("2");
                userDto dto=dao.findByEmail(email);
               if(dto==null){
               BCryptPasswordEncoder bCryptPasswordEncoder=security.pwdEncoder();
               
                userDto userDto=new userDto(0, email, "kim", bCryptPasswordEncoder.encode("1111"), "ROLE_USER");  
                dao.save(userDto);
 
               }
               Authentication authentication=jwtGetTokenService.getAuthentication(dto);
            
               System.out.println("토큰 발급시작");

               String jwtToken=jwtGetTokenService.getJwtToken(dto.getEmail());
               SecurityContextHolder.getContext().setAuthentication(authentication);
               String refeshToken=jwtGetTokenService.getRefreshToken();
               jwtGetTokenService.insertRefreshToken("Bearer "+refeshToken,dto.getEmail());
               System.out.println(jwtToken+" 토큰");

               response.setHeader("refreshToken","Bearer "+refeshToken);
               response.setHeader("Authorization", "Bearer "+jwtToken);
                 
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("LoginNaver 오류가 발생 했습니다");
        }finally{
            headers.clear();
        }
     }
     
}
