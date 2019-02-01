package ru.ifmo.se.termwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.ifmo.se.termwork.domain.User;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected User retrieveUser(String username, UsernamePasswordAuthenticationToken auth)
            throws AuthenticationException {
        return jwtUtils.getAuthentication(String.valueOf(auth.getCredentials()));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

    }
}
