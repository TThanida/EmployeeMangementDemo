package com.example.employeemanagement.service.auth;

public interface AuthenticationService {
    boolean isAuthenticated(String username, String password);

    void validateToken(String token);
}
