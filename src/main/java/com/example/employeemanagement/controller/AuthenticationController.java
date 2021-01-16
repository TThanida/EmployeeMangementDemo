package com.example.employeemanagement.controller;

import com.example.employeemanagement.config.ApplicationConfig;
import com.example.employeemanagement.controller.view.AuthRequest;
import com.example.employeemanagement.controller.view.AuthResponse;
import com.example.employeemanagement.dao.entity.User;
import com.example.employeemanagement.exception.UserNotFoundException;
import com.example.employeemanagement.service.auth.AuthenticationService;
import com.example.employeemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationConfig applicationConfig;

    @PostMapping("/auth/token")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest authRequest) throws UserNotFoundException, AuthenticationException {
        User user = userService.findByUsername(authRequest.getUsername());
        if (user == null) {
            throw new UserNotFoundException(String.format("Username [%s] doesn't exists",authRequest.getUsername()));
        }
        if (!authenticationService.isAuthenticated(authRequest.getUsername(),authRequest.getPassword())) {
            throw new AuthenticationException(String.format("Invalid username or password"));
        }
        //generate token and update token store
        String token = userService.updateUserToken(authRequest.getUsername());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);
        return authResponse;
    }
}
