package com.demo.bankapp.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 6338728573504497502L;

	public BadRequestException() {
		super("Request is malformed.");
	}

	public BadRequestException(String message) {
		super("Request is malformed: " + message);
	}

}
