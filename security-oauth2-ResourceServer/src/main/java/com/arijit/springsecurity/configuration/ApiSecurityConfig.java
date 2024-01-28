package com.arijit.springsecurity.configuration;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.csrf(csrfCustomizer -> csrfCustomizer.disable());
        http.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer->frameOptionsCustomizer.disable()));
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //instruction to spring security to not create any session
                .cors( corsCustomizer ->  corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8090"));//communication from which specific is allowed
                configuration.setAllowedMethods(Collections.singletonList("*"));//which HTTP method is allowed
                configuration.setAllowCredentials(true);//ok to receive credentials for processing
                configuration.setAllowedHeaders(Collections.singletonList("*"));//allow all headers from another origin
                configuration.setExposedHeaders(Arrays.asList("Authorization")); //header to send JWT token to UI
                configuration.setMaxAge(3600L);//browser will remember this configuration for 1 hour
                return configuration;
                }
                }))
                .oauth2ResourceServer(oauth2ResourceServerCustomizer -> oauth2ResourceServerCustomizer.jwt(jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/bird").hasRole("ADMIN")
                .requestMatchers("/animal").hasAnyRole("ADMIN","USER")
                .requestMatchers("/plant").hasRole("USER")
                .requestMatchers("/car","/custom_login").authenticated()//no authorization
                .requestMatchers("/h2-console/**", "/register").permitAll()
                .requestMatchers("/insect").denyAll())
                //.formLogin(withDefaults())
                //.httpBasic(withDefaults())
                .build();
    }


}
