package com.arijit.springsecurity.configuration;

import com.arijit.springsecurity.filter.JWTTokenGenerationFilter;
import com.arijit.springsecurity.filter.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //instruction to spring security to not create any session
                .and()
                .cors().configurationSource(new CorsConfigurationSource() {
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
                }).and()
                .addFilterAfter(new JWTTokenGenerationFilter(), BasicAuthenticationFilter.class)//jwt token generation will start after BasicAuthenticationFilter
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)//jwt token validation will start before BasicAuthenticationFil
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/bird").hasAuthority("admin")
                .requestMatchers("/animal").hasAnyAuthority("admin","user")
                .requestMatchers("/plant").hasAuthority("user")
                .requestMatchers("/car","/custom_login").authenticated()//no authorization
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
