package com.arijit.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrfCustomizer -> csrfCustomizer.disable());
        http.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer->frameOptionsCustomizer.disable()));
        return http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/home").hasAnyAuthority("user","admin")
                .requestMatchers("/","login","/h2-console/**","/register","/access-denied").permitAll()
                .requestMatchers("/manage-employee").hasAuthority("admin")) //onlu admin can access
                .formLogin(form->form.loginPage("/login").defaultSuccessUrl("/home")
                .usernameParameter("username").passwordParameter("password")) //copy 'name' from /login.html form fields
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))
                .logout(request-> request.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
