package com.example.employeemanagement.controller;

import com.example.employeemanagement.controller.view.EmployeeRequest;
import com.example.employeemanagement.controller.view.EmployeeResponse;
import com.example.employeemanagement.dao.entity.Employee;
import com.example.employeemanagement.exception.ObjectNotFoundException;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.ModelMapperService;
import com.example.employeemanagement.service.utils.ValidatorUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/private")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ModelMapperService modelMapperService;

    @PostMapping(value = "/employee")
    @ApiOperation(value = "", authorizations = { @Authorization(value="Bearer") })
    public EmployeeResponse saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        ValidatorUtils.validateEmailFormat(employeeRequest.getEmail());
        //TODO: validate existing e-mail
        Employee employee = modelMapperService.mapFromEmployeeRequest(employeeRequest);
        employee = employeeService.createEmployee(employee);
        return modelMapperService.mapToEmployeeResponse(employee);
    }

    @PutMapping(value = "/employee/{id:[\\d]+}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="Bearer") })
    public EmployeeResponse updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) throws ObjectNotFoundException {
        if (employeeService.findById(id) == null) {
            throw new ObjectNotFoundException(String.format("Employee id [%d] doesn't exists",id));
        }
        ValidatorUtils.validateEmailFormat(employeeRequest.getEmail());
        //TODO: validate existing e-mail
        Employee employee = modelMapperService.mapFromEmployeeRequest(employeeRequest);
        employee.setId(id);
        employee = employeeService.save(employee);
        return modelMapperService.mapToEmployeeResponse(employee);
    }

    @GetMapping(value = "/employee/{id:[\\d]+}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="Bearer") })
    public EmployeeResponse getEmployeeById(@PathVariable Long id) throws ObjectNotFoundException {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ObjectNotFoundException(String.format("Employee id [%d] doesn't exists",id));
        }
        return modelMapperService.mapToEmployeeResponse(employee);
    }

    @GetMapping(value = "/employees")
    @ApiOperation(value = "", authorizations = { @Authorization(value="Bearer") })
    public List<EmployeeResponse> getAllEmployee() throws ObjectNotFoundException {
        List<Employee> employeeList = employeeService.findAll();
        return employeeList.stream().map(e -> modelMapperService.mapToEmployeeResponse(e)).collect(Collectors.toList());
    }

    @DeleteMapping(value = "/employee/{id:[\\d]+}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="Bearer") })
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
