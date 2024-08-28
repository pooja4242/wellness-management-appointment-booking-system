/*package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password); // In real applications, hash passwords
    }
    public boolean existsByEmail(String email) {
    	return userRepository.existsByEmail(email);
    }
    
    public List<User> getAll(){
    	return userRepository.findAll();
    }

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public User updateUser(Long id, User updatedUser) {
		 if (!userRepository.existsById(id)) {
	            throw new RuntimeException("Appointment not found");
	        }
	        updatedUser.setUserid(id);
	        return userRepository.save(updatedUser);
	}
} */

package com.example.service;


import com.example.entity.User;
import com.example.repository.UserRepository;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exception.*;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Save a user in the database.
    public User saveUser(User user) {
    	// Check if the email is already in use
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        return userRepository.save(user);
    }
    
    //Find a user by their email address.
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User with email " + email + " is not registered");
        }
        return user;
    }

    //Authenticate a user by their email and password.
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password); // In real applications, hash passwords
    }

//Check if a user exists by their email address.
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //Retrieve all users from the database.
    public List<User> getAll() {
        return userRepository.findAll();
    }

//Delete a user by their ID.
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
        	throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    //Update user details (except email) by their ID.
    public User updateUser(Long id, User updatedUser) {
        // Check if the user exists
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if the email is being changed
        if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
            throw new EmailChangeNotAllowedException("Cannot change email ID.");
        }

        // Proceed with updating the user details (except email)
        updatedUser.setUserid(id);
        return userRepository.save(updatedUser);
    }
}
