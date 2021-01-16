package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dao.EmployeeRepository;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class EmployeeServiceImpl extends DefaultServiceImpl<Employee> implements EmployeeService {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void setRepository() {
        this.repository = employeeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee createEmployee(Employee employee) {
        employee.setHiredDate(new Date());
        Employee savedEmployee = this.repository.save(employee);
        userService.createUser(employee);
        return savedEmployee;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        userService.deleteById(id);
        super.deleteById(id);
    }
}
