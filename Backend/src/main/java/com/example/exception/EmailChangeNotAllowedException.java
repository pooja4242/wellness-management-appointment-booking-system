package com.example.exception;


/**
 * Custom exception class to handle scenarios where email changes are not allowed.
 * This extends RuntimeException, so it is an unchecked exception.
 */
public class EmailChangeNotAllowedException extends RuntimeException {
	
	
	
	private static final long serialVersionUID = 1L; 
	
    public EmailChangeNotAllowedException(String message) {
        super(message);
    }
}
