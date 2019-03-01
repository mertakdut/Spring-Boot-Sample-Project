package com.demo.bankapp.exception;

public class BadCredentialsException extends RuntimeException {

	public BadCredentialsException() {
		super("Bad Credentials.");
	}

	public BadCredentialsException(String message) {
		super("Bad Credentials: " + message);
	}

}
