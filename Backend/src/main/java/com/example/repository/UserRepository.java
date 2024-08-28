package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {     // Extends JpaRepository to provide CRUD operations and query methods for User entities.
	
	boolean existsByEmail(String email); // Checks if a user with the specified email already exists in the database.
    User findByEmail(String email); //Finds a user by their email address.

    
	
}