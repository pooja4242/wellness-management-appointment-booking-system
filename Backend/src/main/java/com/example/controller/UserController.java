package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="http://localhost:3000") // Allows cross-origin requests from localhost:3000 (frontend)
public class UserController {

    @Autowired
    private UserService userService;
    
    //Create or update Users
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // HTTP 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // HTTP 500 Internal Server Error
        }
    }
    
    //endpoint to verify the server is running
    @GetMapping
    public String healthCheck() {
        return "Server is up and running!";
    }
    
    //Get all users
    @GetMapping("/getAll")
    public List<User> getAllUsers(){
    	return userService.getAll();
    }
    
    //get user by email
    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    //login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isAuthenticated = userService.authenticateUser(user.getEmail(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login Successful"); // HTTP 200 OK
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials"); // HTTP 401 Unauthorized
        }
    }
    
    //update the user by id
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
    
    //delete user by id
    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
		 if(id!=null) {
	        userService.deleteUserById(id);
		 }
	        return "Appointment with ID " + id + " deleted successfully.";
	    }
    
    
   
}