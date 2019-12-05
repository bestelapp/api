package com.fontys.backend.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("https://bestel-app-web.herokuapp.com/", "http://localhost:4200")
                .allowedMethods("POST","GET")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
