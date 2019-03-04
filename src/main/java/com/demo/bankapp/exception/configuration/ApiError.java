package com.demo.bankapp.exception.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ApiError(final HttpStatus status, final String message, final List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ApiError(final HttpStatus status, final String message, final String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

}
