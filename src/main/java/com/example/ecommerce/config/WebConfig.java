package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return addCorsMappings(registry) -> {
//            registry.addMapping("/**")
//                    .allowedOrigins("*")
//                    .allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS");
//        };
//    }
}
