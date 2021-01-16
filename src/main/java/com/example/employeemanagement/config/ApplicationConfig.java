package com.example.employeemanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    public static final String USER_FIRST_TIME_PASSWORD = "12345678"; //TODO: change to a better standard way
    @Value("${user.admin.username}")
    private String adminUsername;
    @Value("${user.admin.password}")
    private String adminPassword;

    @Value("${token.expired.seconds:3600}")
    private int tokenExpiredSeconds;

    @Bean
    public static final PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public int getTokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }
}
