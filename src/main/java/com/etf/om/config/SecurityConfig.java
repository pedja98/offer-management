package com.etf.om.config;

import com.etf.om.filters.HeaderValidationFilter;
import com.etf.om.filters.SetCurrentUserFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HeaderValidationFilter headerValidationFilter, SetCurrentUserFilter setCurrentUserFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                ).addFilterBefore(headerValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(setCurrentUserFilter, HeaderValidationFilter.class);
        return http.build();
    }
}
