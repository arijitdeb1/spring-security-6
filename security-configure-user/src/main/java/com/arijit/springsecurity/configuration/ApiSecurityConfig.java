package com.arijit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/car", "/bird").authenticated()
                .requestMatchers("/animal", "/plant").permitAll()
                .requestMatchers("/insect").denyAll())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


    @Bean
    public InMemoryUserDetailsManager registerUser() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                            .username("admin")
                            .password("admin")
                            .authorities("admin")
                            .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                            .username("user")
                            .password("user")
                            .authorities("user")
                            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

}
