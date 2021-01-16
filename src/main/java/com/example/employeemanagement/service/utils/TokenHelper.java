package com.example.employeemanagement.service.utils;

import java.util.UUID;

public class TokenHelper {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
