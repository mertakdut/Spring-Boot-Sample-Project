package com.demo.bankapp.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1360953961105975949L;

	public UserNotFoundException() {
		super("User wealth not found");
	}

	public UserNotFoundException(String username) {
		super("Could not find user " + username);
	}

}
