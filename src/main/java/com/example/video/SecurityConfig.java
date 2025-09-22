package com.example.video;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (especially for local dev)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/**").permitAll()  // Allow public access to /api/**
                                .anyRequest().authenticated()           // Secure all other routes
                )
                .httpBasic(withDefaults()); // Optional: enables basic auth login if needed
        return http.build();
    }
}
