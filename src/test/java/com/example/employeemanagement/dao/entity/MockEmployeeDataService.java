package com.example.employeemanagement.dao.entity;

import com.example.employeemanagement.controller.view.EmployeeRequest;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class MockEmployeeDataService {

    public Employee employee1() {
        Employee employee = new Employee();
        employee.setFirstName("Thanida");
        employee.setLastName("Tangkocharoen");
        employee.setNickName("Aom");
        employee.setAddress("105/2 Sathupradit 34 Rd., Yannawa District, Bangpongpang Sub-district");
        employee.setCity("Bangkok");
        employee.setCountry("Thailand");
        employee.setPostCode(10120);
        employee.setTelephone("0909037073");
        employee.setEmail("t.thanida50@gmail.com");
        employee.setHiredDate(new Date());
        return employee;
    }
    public Employee employee2() {
        Employee employee = new Employee();
        employee.setFirstName("TThanida");
        employee.setLastName("Tang");
        employee.setNickName("Aom");
        employee.setAddress("105/2 Sathupradit 34 Rd., Yannawa District, Bangpongpang Sub-district");
        employee.setCity("Bangkok");
        employee.setCountry("Thailand");
        employee.setPostCode(10120);
        employee.setTelephone("12345678");
        employee.setEmail("s.aomzzz50@gmail.com");
        employee.setHiredDate(new Date());
        return employee;
    }
    public Employee employee3() {
        Employee employee = new Employee();
        employee.setFirstName("TTTThanida");
        employee.setLastName("Tang");
        employee.setNickName("Aom");
        employee.setAddress("105/2 Sathupradit 34 Rd., Yannawa District, Bangpongpang Sub-district");
        employee.setCity("Bangkok");
        employee.setCountry("Thailand");
        employee.setPostCode(10120);
        employee.setTelephone("987654321");
        employee.setEmail("t.aomzzz@gmail.com");
        employee.setHiredDate(new Date());
        return employee;
    }
    public EmployeeRequest employee1Request() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        BeanUtils.copyProperties(employee1(),employeeRequest);
        return employeeRequest;
    }
}
