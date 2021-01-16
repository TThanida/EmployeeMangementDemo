package com.example.employeemanagement;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestJavaFunction {
    private Logger logger = LoggerFactory.getLogger(TestJavaFunction.class);

    @Test
    public void encodePassword() {
        String password = "P@ssw0rd";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        logger.info("encoded: "+encodedPassword);
        assertNotEquals(password,encodedPassword);
    }
}
