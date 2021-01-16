package com.example.employeemanagement.controller.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"username","password"})
public class AuthRequest {
    @NotBlank(message = "[username] is required")
    @ApiModelProperty(example = "admin@dummy.com")
    private String username;
    @NotBlank(message = "[password] is required")
    @ApiModelProperty(example = "P@ssw0rd")
    private String password;
}
