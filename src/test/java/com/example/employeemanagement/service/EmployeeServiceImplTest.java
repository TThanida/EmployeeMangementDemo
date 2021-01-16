package com.example.employeemanagement.service;

import com.example.employeemanagement.dao.EmployeeRepository;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.MockEmployeeDataService;
import com.example.employeemanagement.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private UserService userService;
    @Mock
    private EmployeeRepository employeeRepository;
    private MockEmployeeDataService mockEmployeeDataService = new MockEmployeeDataService();

    @Test
    public void shouldSaveEmployeeSuccess() {
        Employee mockEmployee = mockEmployeeDataService.employee1();
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
        Employee employee = employeeService.save(mockEmployee);
        assertNotNull(employee);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    public void shouldCreateEmployeeSuccess() {
        Employee mockEmployee = mockEmployeeDataService.employee1();
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
        Employee employee = employeeService.createEmployee(mockEmployee);
        assertNotNull(employee);
        verify(employeeRepository).save(any(Employee.class));
        verify(userService).createUser(any(Employee.class));
    }

    @Test
    public void shouldFindByEmployeeId() {
        Long id = 1L;
        Employee mockEmployee = mockEmployeeDataService.employee1();
        mockEmployee.setId(id);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));
        Employee employee = employeeService.findById(id);
        assertNotNull(employee);
        assertEquals(id,employee.getId());
        verify(employeeRepository).findById(anyLong());
    }

    @Test
    public void shouldFindAllEmployee() {
        List<Employee> mockEmployeeList = Arrays.asList(
                mockEmployeeDataService.employee1(),
                mockEmployeeDataService.employee2(),
                mockEmployeeDataService.employee3());
        List<String> expectedEmployeeNames = mockEmployeeList.stream().map(Employee::getFirstName).collect(Collectors.toList());

        when(employeeRepository.findAll()).thenReturn(mockEmployeeList);
        List<Employee> employeeList = employeeService.findAll();
        assertNotNull(employeeList);
        assertTrue(employeeList.size() > 0);
        List<String> actualEmployeeNames = employeeList.stream().map(Employee::getFirstName).collect(Collectors.toList());
        assertEquals(expectedEmployeeNames.size(),actualEmployeeNames.size());
        assertTrue(actualEmployeeNames.containsAll(expectedEmployeeNames) && expectedEmployeeNames.containsAll(actualEmployeeNames));
        verify(employeeRepository).findAll();
    }

    @Test
    public void shouldDeleteEmployeeSuccess() {
        Long id = 1L;
        employeeService.deleteById(id);
        verify(userService).deleteById(id);
        verify(employeeRepository).deleteById(id);
    }

}