package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.config.ApplicationConfig;
import com.example.employeemanagement.dao.UserRepository;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.User;
import com.example.employeemanagement.service.UserService;
import com.example.employeemanagement.service.utils.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class UserServiceImpl extends DefaultServiceImpl<User> implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setRepository() {
        this.repository = userRepository;
    }

    @Override
    public User findByUsername(String email) {
        return this.userRepository.findByUsername(email);
    }

    @Override
    public User findByToken(String token) {
        return this.userRepository.findByToken(token);
    }

    @Override
    public void createUser(Employee employee) {
        User user = new User();
        user.setUsername(employee.getEmail()); //set employee e-mail as username
        user.setPassword(passwordEncoder.encode(ApplicationConfig.USER_FIRST_TIME_PASSWORD)); //set first time password by the default password
        userRepository.save(user);
    }

    @Override
    public String updateUserToken(String username) {
        User user = userRepository.findByUsername(username);
        String token = TokenHelper.generateToken();
        user.setToken(token);
        user.setTokenCreatedDate(new Date());
        userRepository.save(user);
        return token;
    }
}
