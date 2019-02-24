package com.demo.bankapp.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String username) {
		super("Could not find user " + username);
	}

}
