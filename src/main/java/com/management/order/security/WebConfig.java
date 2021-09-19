package com.management.order.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://ec2-15-152-36-249.ap-northeast-3.compute.amazonaws.com:8080/")
                .allowedOrigins("http://ec2-15-152-36-249.ap-northeast-3.compute.amazonaws.com:8080")
                .allowedOrigins("http://localhost:8080")
                .allowCredentials(true);
    }
}
