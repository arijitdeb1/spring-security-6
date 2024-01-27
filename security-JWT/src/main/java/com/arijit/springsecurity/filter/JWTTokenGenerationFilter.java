package com.arijit.springsecurity.filter;

import com.arijit.springsecurity.constant.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
this filter will be executed after BasicAuthenticationFilter
 */
public class JWTTokenGenerationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//fetch details of already authenticated by BasicAuthenticationFilter
        if(null != authentication){
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8)); //will be used as JWT signature
            String jwt = Jwts.builder().setIssuer("Arijit").setSubject("JWT Token")
                    .claim("username",authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+ 30000000))//8 hours   30000 (30 seconds)
                    .signWith(key).compact(); //framing the jwt token
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);//setting the jwt token to Authorization header

        }
        filterChain.doFilter(request, response);
    }


    /*
    above filter will only execute when requested api is /login
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !request.getServletPath().equals("/custom_login");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority:collection){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",",authoritiesSet);
    }
}
