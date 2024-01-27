package com.arijit.springsecurity.filter;

import com.arijit.springsecurity.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER).split(" ")[1]; //remove 'Bearer' and fetch the token from Authorization header
        if(null != jwt){
            try{
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims= Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody(); // validate jwt and fetch detailss
                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));
                Authentication authentication =  new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));//creates Authentication object after successful authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("Invalid Token Received");

            }

        }
        filterChain.doFilter(request, response);
    }


    /*
    above filter will only execute when requested api is NOT /login
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getServletPath().equals("/custom_login");
    }
}
