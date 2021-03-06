package com.si2001.webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS", "HEAD", "PATCH")
                        .allowedHeaders("*")
                        .exposedHeaders("header1", "header2", "Authorization")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }
}
