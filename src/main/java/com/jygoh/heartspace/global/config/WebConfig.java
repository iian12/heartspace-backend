package com.jygoh.heartspace.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://jiangxy.github.io")
            .exposedHeaders("Authorization")
            .exposedHeaders("WWW-Authenticate")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "CONNECT")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
