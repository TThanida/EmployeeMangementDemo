package com.example.employeemanagement.controller.view;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String telephone;
    private String address;
    private String city;
    private String country;
    private Integer postCode;
    private Date hiredDate;
    private Date createdDate;
    private Date modifiedDate;
    private int version;
}
