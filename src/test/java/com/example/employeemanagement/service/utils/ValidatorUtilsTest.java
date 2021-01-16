package com.example.employeemanagement.service.utils;

import org.junit.Test;

public class ValidatorUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void giveIncorrectEmail_shouldThrowError() {
        ValidatorUtils.validateEmailFormat("t.thanida");
    }

    @Test
    public void shouldValidateEmailSuccess() {
        ValidatorUtils.validateEmailFormat("t.thanida50@gmail.com");
    }
}