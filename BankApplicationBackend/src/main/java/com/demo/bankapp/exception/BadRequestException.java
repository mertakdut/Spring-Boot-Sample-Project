package com.demo.bankapp.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException() {
		super("Request is malformed.");
	}

}
