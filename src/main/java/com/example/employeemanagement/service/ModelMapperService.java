package com.example.employeemanagement.service;

import com.example.employeemanagement.controller.view.EmployeeRequest;
import com.example.employeemanagement.controller.view.EmployeeResponse;
import com.example.employeemanagement.dao.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperService {

    public EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee,employeeResponse);
        return employeeResponse;
    }

    public Employee mapFromEmployeeRequest(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest,employee);
        return employee;
    }
}
