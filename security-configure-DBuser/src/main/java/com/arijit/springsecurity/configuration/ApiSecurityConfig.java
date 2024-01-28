package com.arijit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfCustomizer -> csrfCustomizer.disable());
        http.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer->frameOptionsCustomizer.disable()));
        return http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/car", "/bird").authenticated()
                .requestMatchers("/animal", "/plant","/register").permitAll()
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
        return NoOpPasswordEncoder.getInstance();
    }

}
