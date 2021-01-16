package com.example.employeemanagement.service.auth.impl;

import com.example.employeemanagement.config.ApplicationConfig;
import com.example.employeemanagement.dao.UserRepository;
import com.example.employeemanagement.dao.entity.User;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuthenticationServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplTest.class);

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ApplicationConfig applicationConfig;
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void givenIncorrectPassword_shouldVerifyAuthenticateFail() {
        User mockUser = mockUser();
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        boolean isAuthenticated = authenticationService.isAuthenticated(mockUser.getUsername(),"123213");
        assertFalse(isAuthenticated);
    }

    @Test
    public void shouldVerifyAuthenticateSuccess() {
        User mockUser = mockUser();
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        boolean isAuthenticated = authenticationService.isAuthenticated(mockUser.getUsername(),"P@ssw0rd");
        assertTrue(isAuthenticated);
    }

    @Test(expected = BadCredentialsException.class)
    public void giveNotExistsUser_shouldThrowError() {
        String token = mockUser().getToken();
        when(userRepository.findByToken(anyString())).thenReturn(null);
        authenticationService.validateToken(token);
    }

    @Test(expected = BadCredentialsException.class)
    public void giveExpiredToken_shouldThrowError() {
        User mockUser = mockUser();
        when(userRepository.findByToken(anyString())).thenReturn(mockUser);
        when(applicationConfig.getTokenExpiredSeconds()).thenReturn(60);
        //set token create date to more than the specific expired token time
        mockUser.setTokenCreatedDate(new Date((new Date().getTime()-61*1000)));
        String token = mockUser.getToken();
        authenticationService.validateToken(token);
    }

    @Test
    public void giveUnExpiredToken_shouldValidateTokenSuccess() {
        User mockUser = mockUser();
        when(userRepository.findByToken(anyString())).thenReturn(mockUser);
        when(applicationConfig.getTokenExpiredSeconds()).thenReturn(60);
        //set token create date to less than the specific expired token time
        mockUser.setTokenCreatedDate(new Date((new Date().getTime()-59*1000)));
        String token = mockUser.getToken();
        authenticationService.validateToken(token);
        verify(applicationConfig).getTokenExpiredSeconds();
    }

    @Test
    public void shouldValidateTokenSuccess() {
        User mockUser = mockUser();
        when(userRepository.findByToken(anyString())).thenReturn(mockUser);
        when(applicationConfig.getTokenExpiredSeconds()).thenReturn(3600);
        String token = mockUser.getToken();
        authenticationService.validateToken(token);
        verify(applicationConfig).getTokenExpiredSeconds();
    }

    private User mockUser() {
        User user = new User();
        user.setUsername("dummy@dummy.com");
        user.setPassword(passwordEncoder.encode("P@ssw0rd"));
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedDate(new Date());
        return user;
    }
}