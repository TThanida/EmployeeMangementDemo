package com.example.employeemanagement.service.auth.impl;

import com.example.employeemanagement.config.ApplicationConfig;
import com.example.employeemanagement.dao.UserRepository;
import com.example.employeemanagement.dao.entity.User;
import com.example.employeemanagement.service.auth.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (passwordEncoder.matches(password,user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void validateToken(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            throw new BadCredentialsException("Invalid token");
        }
        if (user.getTokenCreatedDate() == null) {
            return; //TODO: add logic to manage token without created date
        }
        if ((new Date().getTime() - user.getTokenCreatedDate().getTime())/1000 > applicationConfig.getTokenExpiredSeconds()) {
            throw new BadCredentialsException("Token is expired");
        }
    }

}
