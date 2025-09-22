package com.example.video;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply this to your API endpoints
                .allowedOrigins("http://localhost:5173")  // Allow requests from React app
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowCredentials(true)  // Allow credentials (cookies, authorization headers)
                .allowedHeaders("*");  // Allow all headers
    }
}
