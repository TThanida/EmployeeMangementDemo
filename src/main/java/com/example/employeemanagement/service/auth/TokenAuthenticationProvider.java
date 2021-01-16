package com.example.employeemanagement.service.auth;

import com.example.employeemanagement.dao.entity.User;
import com.example.employeemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("authenticate...");
        String token = (String)authentication.getCredentials();
        logger.debug("validate token...");
        authenticationService.validateToken(token);
        User user = userService.findByToken(token);
        return new UsernamePasswordAuthenticationToken(user.getUsername(),token, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
