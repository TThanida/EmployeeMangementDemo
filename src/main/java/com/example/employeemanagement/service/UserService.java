package com.example.employeemanagement.service;

import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.User;

public interface UserService extends DefaultService<User> {
    User findByUsername(String email);

    User findByToken(String token);

    void createUser(Employee employee);

    String updateUserToken(String username);
}
