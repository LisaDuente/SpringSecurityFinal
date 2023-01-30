package com.workshop.Lisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Priority;

@Profile("develop")
@EnableWebSecurity
public class DevelopSecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .formLogin()
                .and()
                .build();
    }

}
