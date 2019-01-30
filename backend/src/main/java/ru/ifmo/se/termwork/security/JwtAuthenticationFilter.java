package ru.ifmo.se.termwork.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String SCHEMA = "Bearer";

    public JwtAuthenticationFilter(RequestMatcher requestMatcher){
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String header = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).
                orElseThrow(() -> new BadCredentialsException("Missing authentication token"));

        String token = header.substring(SCHEMA.length()).trim();
        Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);

        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
