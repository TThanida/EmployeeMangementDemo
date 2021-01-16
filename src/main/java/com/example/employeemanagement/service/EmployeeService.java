package com.example.employeemanagement.service;

import com.example.employeemanagement.dao.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeService extends DefaultService<Employee> {
    @Transactional(rollbackFor = Exception.class)
    Employee createEmployee(Employee employee);
}
