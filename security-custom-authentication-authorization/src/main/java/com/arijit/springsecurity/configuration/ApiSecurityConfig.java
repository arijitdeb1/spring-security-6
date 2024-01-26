package com.arijit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.authorizeHttpRequests((requests) -> requests
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
