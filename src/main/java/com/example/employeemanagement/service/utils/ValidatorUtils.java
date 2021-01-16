package com.example.employeemanagement.service.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidatorUtils {

    public static void validateEmailFormat(String email) {
        boolean valid = EmailValidator.getInstance().isValid(email);
        if (!valid) {
            throw new IllegalArgumentException(String.format("email [%s] is incorrect format",email));
        }
    }
}
