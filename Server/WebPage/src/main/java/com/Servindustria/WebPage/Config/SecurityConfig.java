package com.Servindustria.WebPage.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.cors(withDefaults()).csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(authRequest -> authRequest.requestMatchers("/img_products/**", "/css/**", "/js/**", "/assets/**").permitAll().requestMatchers("/api/**").permitAll().requestMatchers("/auth/**").permitAll().anyRequest().authenticated()).sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
 
    }
}
