package com.example.employeemanagement.dao;

import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.MockEmployeeDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private MockEmployeeDataService mockEmployeeDataService = new MockEmployeeDataService();

    @Test(expected = Exception.class)
    public void givenNullEmployee_shouldNotSaveAndThrowError() {
        employeeRepository.save(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void givenSomeEmployeeDetail_shouldNotSaveAndThrowError() {
        Employee employee = new Employee();
        employee.setFirstName("Thanida");
        employeeRepository.save(employee);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void givenDuplicateEmployeeEmail_shouldNotSaveAndThrowError() {
        String email = "t.thanida50@gmail.com";
        Employee employee1 = mockEmployeeDataService.employee1();
        employee1.setEmail(email);
        Employee employee2 = mockEmployeeDataService.employee2();
        employee2.setEmail(email);
        employeeRepository.saveAndFlush(employee1);
        employeeRepository.saveAndFlush(employee2);
    }

    @Test
    public void givenAllEmployeeDetail_shouldSaveSuccess() {
        Employee employee = mockEmployeeDataService.employee1();
        employee = employeeRepository.save(employee);
        assertNotNull(employee);
        assertNotNull(employee.getId());
    }

    @Test
    public void shouldFindEmployeeById() {
        employeeRepository.save(mockEmployeeDataService.employee1());
        Employee employee = employeeRepository.findById(1L).orElse(null);
        assertNotNull(employee);
    }

    @Test
    public void shouldFindAllEmployees() {
        employeeRepository.save(mockEmployeeDataService.employee1());
        employeeRepository.save(mockEmployeeDataService.employee2());
        employeeRepository.save(mockEmployeeDataService.employee3());
        List<Employee> employeeList = employeeRepository.findAll();
        assertNotNull(employeeList);
        assertTrue(employeeList.size() > 0);
    }

    @Test
    public void shouldUpdateEmployeeDetails() {
        employeeRepository.save(mockEmployeeDataService.employee1());
        Employee employee = employeeRepository.findById(1L).orElse(null);
        assertNotNull(employee);
        String oldFirstName = employee.getFirstName();
        String oldLastName = employee.getLastName();
        String oldAddress = employee.getAddress();
        String oldTelephone = employee.getTelephone();
        employee.setFirstName("Shanida");
        employee.setLastName("Sharara");
        employee.setAddress("123/4 ABC Road, AOT District");
        employee.setPostCode(11111);
        employee.setTelephone("7777777777");
        employee = employeeRepository.findById(1L).orElse(null);
        assertNotNull(employee);
        assertNotEquals(oldFirstName,employee.getFirstName());
        assertNotEquals(oldTelephone,employee.getTelephone());
        assertNotEquals(oldLastName,employee.getLastName());
        assertNotEquals(oldAddress,employee.getAddress());
        assertNotEquals(oldTelephone,employee.getTelephone());
    }

    @Test
    public void shouldDeleteEmployeeById() {
        Employee employee = employeeRepository.save(mockEmployeeDataService.employee1());
        employeeRepository.deleteById(employee.getId());
        employee = employeeRepository.findById(employee.getId()).orElse(null);
        assertNull(employee);
    }

    @Test
    public void shouldDeleteAllEmployee() {
        employeeRepository.save(mockEmployeeDataService.employee1());
        employeeRepository.save(mockEmployeeDataService.employee2());
        employeeRepository.save(mockEmployeeDataService.employee3());
        List<Employee> employeeList = employeeRepository.findAll();
        assertNotNull(employeeList);
        assertTrue(employeeList.size() > 0);
        employeeRepository.deleteAll();
        employeeList = employeeRepository.findAll();
        assertTrue(employeeList.size() == 0);
    }

}