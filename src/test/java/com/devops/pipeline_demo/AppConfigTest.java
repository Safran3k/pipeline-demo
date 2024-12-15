package com.devops.pipeline_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoderBean() {
        assertNotNull(passwordEncoder);

        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}