package com.example.BusTicketBookingBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu cần)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Cho phép tất cả request
                .formLogin(login -> login.disable()) // Tắt form login
                .httpBasic(httpBasic -> httpBasic.disable()); // Tắt Basic Authentication

        return http.build();
    }
}
