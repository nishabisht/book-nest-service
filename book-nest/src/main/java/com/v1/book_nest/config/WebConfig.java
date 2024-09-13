package com.v1.book_nest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/h2-console/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
