package com.example.jwttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class corsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowCredentials(true);//내 서버가 응답할때 json을 자바스크립트에서 처리 가능여부
        configuration.addAllowedOrigin("*");///모든 ip에 응답허용
        configuration.addAllowedHeader("*");///모든 헤더에 응답허용
        configuration.addAllowedMethod("*");///모든 post/get/put등 모든 요청 허용
        source.registerCorsConfiguration("/**", configuration);// /api/로 오는 모든 경로는 이 configurationd을 타야한다
        return new CorsFilter(source);
    }
}
