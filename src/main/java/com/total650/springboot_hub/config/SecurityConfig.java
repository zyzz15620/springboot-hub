package com.total650.springboot_hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Disable form login to only use Basic Authentication
        http.csrf((csrf) -> csrf.disable()).
                authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
