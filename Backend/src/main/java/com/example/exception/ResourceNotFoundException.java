package com.example.exception;



/**
 * Custom exception class to handle scenarios where a requested resource is not found.
 * This extends RuntimeException, making it an unchecked exception.
 */
public class ResourceNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}