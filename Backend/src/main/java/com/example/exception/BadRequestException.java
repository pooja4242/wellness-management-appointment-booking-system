package com.example.exception;



/**
 * Custom exception class for handling bad requests.
 * Extends RuntimeException to represent exceptions related to invalid or malformed requests.
 */
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
        super(message);
    }
}