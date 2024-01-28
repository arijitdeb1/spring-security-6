package com.arijit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfCustomizer->csrfCustomizer.ignoringRequestMatchers("/register","/h2-console/**"));
        http.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer->frameOptionsCustomizer.disable()));
        return http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/car", "/bird").authenticated()
                .requestMatchers("/animal", "/plant","/testCSRF","/register").permitAll()
                .requestMatchers("/insect").denyAll())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
