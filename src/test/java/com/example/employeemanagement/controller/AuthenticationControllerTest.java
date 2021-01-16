package com.example.employeemanagement.controller;

import com.example.employeemanagement.config.ApplicationConfig;
import com.example.employeemanagement.controller.view.AuthRequest;
import com.example.employeemanagement.dao.entity.User;
import com.example.employeemanagement.service.UserService;
import com.example.employeemanagement.service.auth.AuthenticationService;
import com.example.employeemanagement.service.auth.TokenAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
    private Logger logger = LoggerFactory.getLogger(AuthenticationControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ApplicationConfig applicationConfig;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenEmptyRequest_shouldAuthenticateFail() throws Exception {
        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void givenNotExistsUser_shouldAuthenticateSuccess() throws Exception {
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("P@ssw0rd");
        when(userService.findByUsername(anyString())).thenReturn(null);
        when(authenticationService.isAuthenticated(anyString(),anyString())).thenReturn(true);
        when(userService.updateUserToken(anyString())).thenReturn(token);

        mockMvc.perform(post("/auth/token")
                .content(objectMapper.writeValueAsString(new AuthRequest(user.getUsername(),user.getPassword())))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userService).findByUsername(anyString());
    }

    @Test
    public void shouldAuthenticateSuccess() throws Exception {
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("P@ssw0rd");
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(authenticationService.isAuthenticated(anyString(),anyString())).thenReturn(true);
        when(userService.updateUserToken(anyString())).thenReturn(token);

        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AuthRequest(user.getUsername(),user.getPassword()))))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessToken").value(token));

        verify(userService).findByUsername(anyString());
        verify(authenticationService).isAuthenticated(anyString(),anyString());
        verify(userService).updateUserToken(anyString());
    }
}