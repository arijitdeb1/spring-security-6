package com.arijit.springsecurity.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfCustomizer -> csrfCustomizer.disable());
        http.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer->frameOptionsCustomizer.disable()));
        return http.cors( corsCustomizer ->  corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8090"));//communication from which specific is allowed
                configuration.setAllowedMethods(Collections.singletonList("*"));//which HTTP method is allowed
                configuration.setAllowCredentials(true);//ok to receive credentials for processing
                configuration.setAllowedHeaders(Collections.singletonList("*"));//allow all headers from another origin
                configuration.setMaxAge(3600L);//browser will remember this configuration for 1 hour
                return configuration;
                }}))
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/bird").hasAuthority("admin")
                .requestMatchers("/animal").hasAnyAuthority("admin","user")
                .requestMatchers("/plant").hasAuthority("user")
                .requestMatchers("/car").authenticated()//no authorization
                .requestMatchers("/h2-console/**", "/register").permitAll()
                .requestMatchers("/insect").denyAll())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


    //below bean is ambiguous to EmployeeDetailsService in service package
    //To use below implementation, comment out/delete EmployeeDetailsService class
    //and uncomment below bean implementation
    /**
     * @param dataSource holds all DB properties from application.properties
     * @return
     */
    /*@Bean
    public UserDetailsService registerUser(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
