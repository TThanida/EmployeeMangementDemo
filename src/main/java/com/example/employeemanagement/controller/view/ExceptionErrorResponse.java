package com.example.employeemanagement.controller.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionErrorResponse {
    private String error;
    private String message;
}