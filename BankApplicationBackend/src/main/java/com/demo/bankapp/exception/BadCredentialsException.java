package com.demo.bankapp.exception;

public class BadCredentialsException extends RuntimeException {

	public BadCredentialsException() {
		super("Bad Credentials.");
	}

}
