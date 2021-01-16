package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dao.UserRepository;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.MockEmployeeDataService;
import com.example.employeemanagement.dao.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImplTests {
    private Logger logger = LoggerFactory.getLogger(UserServiceImplTests.class);

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private MockEmployeeDataService mockEmployeeDataService = new MockEmployeeDataService();

    @Before
    public void setUp() {
    }

    @Test
    public void shouldFindUserByUsername() throws Exception {
        User mockUser = mockUser();
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        User user = userService.findByUsername(mockUser.getUsername());
        assertNotNull(user);
        assertEquals(mockUser.getUsername(),user.getUsername());
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    public void shouldFindUserByToken() {
        User mockUser = mockUser();
        when(userRepository.findByToken(anyString())).thenReturn(mockUser);
        User user = userService.findByToken(mockUser.getToken());
        assertNotNull(user);
        assertEquals(mockUser.getUsername(),user.getUsername());
        verify(userRepository).findByToken(anyString());
    }

    @Test
    public void shouldCreateUserSuccess() {
        Employee employee = mockEmployeeDataService.employee1();
        userService.createUser(employee);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldUpdateUserSuccess() {
        User mockUser = mockUser();
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        String token = userService.updateUserToken(mockUser.getUsername());
        assertNotNull(token);
        verify(userRepository).save(any(User.class));
    }

    private User mockUser() {
        User user = new User();
        user.setUsername("dummy@dummy.com");
        user.setPassword("P@ssw0rd");
        user.setToken(UUID.randomUUID().toString());
        return user;
    }
}