package com.example.employeemanagement.controller;

import com.example.employeemanagement.controller.view.EmployeeRequest;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.dao.entity.MockEmployeeDataService;
import com.example.employeemanagement.filter.TokenAuthenticationFilter;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.ModelMapperService;
import com.example.employeemanagement.service.auth.TokenAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @MockBean
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private ModelMapperService modelMapperService;
    private MockEmployeeDataService mockEmployeeDataService = new MockEmployeeDataService();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        when(modelMapperService.mapFromEmployeeRequest(any(EmployeeRequest.class))).thenCallRealMethod();
        when(modelMapperService.mapToEmployeeResponse(any(Employee.class))).thenCallRealMethod();
    }

    @Test
    public void shouldSaveEmployeeSuccess() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(mockEmployeeDataService.employee1());
        mockMvc.perform(post("/private/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockEmployeeDataService.employee1Request())))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService).createEmployee(any(Employee.class));
    }

    @Test
    public void shouldUpdateEmployeeSuccess() throws Exception {
        Long id = 1L;
        Employee mockEmployee = mockEmployeeDataService.employee1();
        mockEmployee.setId(id);
        when(employeeService.findById(id)).thenReturn(mockEmployee);
        String updatedName = "Dummy";
        Employee modifiedEmployee = mockEmployeeDataService.employee1();
        modifiedEmployee.setFirstName(updatedName);
        when(employeeService.save(any(Employee.class))).thenReturn(modifiedEmployee);

        EmployeeRequest modifiedRequest = mockEmployeeDataService.employee1Request();
        modifiedRequest.setFirstName(updatedName);
        MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.put("/private/employee/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifiedRequest));
        mockMvc.perform(mockRequestBuilder)
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedName));
        verify(employeeService).save(any(Employee.class));
    }

    @Test
    public void shouldGetEmployeeByIdSuccess() throws Exception {
        Long id = 1L;
        Employee mockEmployee = mockEmployeeDataService.employee1();
        mockEmployee.setId(id);
        when(employeeService.findById(id)).thenReturn(mockEmployee);
        mockMvc.perform(get("/private/employee/"+id))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").exists());
        verify(employeeService).findById(anyLong());
    }

    @Test
    public void shouldGetAllEmployeesSuccess() throws Exception {
        List<Employee> employeeList = Arrays.asList(
                mockEmployeeDataService.employee1(),
                mockEmployeeDataService.employee2(),
                mockEmployeeDataService.employee3()
        );
        when(employeeService.findAll()).thenReturn(employeeList);
        mockMvc.perform(get("/private/employees"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(employeeList.size())));
        verify(employeeService).findAll();
    }

    @Test
    public void shouldDeleteEmployeeByIdSuccess() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/private/employee/"+id))
                .andDo(print())
                .andExpect(status().isOk());
        verify(employeeService).deleteById(anyLong());
    }

}