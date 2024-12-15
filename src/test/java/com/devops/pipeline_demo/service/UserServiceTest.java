package com.devops.pipeline_demo;

import com.devops.pipeline_demo.models.User;
import com.devops.pipeline_demo.repositories.UserRepository;
import com.devops.pipeline_demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser() {
        String username = "testUser";
        String password = "password123";

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.registerUser(username, password);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testValidateUser_Success() {
        String username = "testUser";
        String password = "password123";

        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        boolean isValid = userService.validateUser(username, password);

        assertTrue(isValid);
    }

    @Test
    void testValidateUser_Failure() {
        String username = "testUser";
        String password = "wrongPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(false);

        boolean isValid = userService.validateUser(username, password);

        assertFalse(isValid);
    }

    @Test
    void testUsernameExists() {
        String username = "testUser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = userService.usernameExists(username);

        assertTrue(exists);
    }
}