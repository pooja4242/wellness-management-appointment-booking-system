package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

 // to ensure it saves a user and handles the email check correctly
    @Test
    void saveUser() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(user);
    }

    //to ensure it retrieves a user by email correctly
    @Test
    void findByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    //to ensure it correctly authenticates a user with valid credentials
    @Test
    void authenticateUser() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(user);

        boolean isAuthenticated = userService.authenticateUser(email, password);

        assertTrue(isAuthenticated);
        verify(userRepository, times(1)).findByEmail(email);
    }

    //to handle invalid credentials
    @Test
    void authenticateUser_InvalidCredentials() {
        String email = "test@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");

        when(userRepository.findByEmail(email)).thenReturn(user);

        boolean isAuthenticated = userService.authenticateUser(email, password);

        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByEmail(email);
    }

    //to ensure it deletes a user if the user exists
    @Test
    void deleteUserById() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    //to handle the case where the user does not exist
    @Test
    void deleteUserById_UserNotFound() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    //to ensure it updates an existing user's information
    @Test
    void updateUser() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUserid(userId);

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(userId, result.getUserid());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(updatedUser);
    }

    //to handle the case where the user to be updated does not exist
    @Test
    void updateUser_UserNotFound() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUserid(userId);

        when(userRepository.existsById(userId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).save(updatedUser);
    }
}
