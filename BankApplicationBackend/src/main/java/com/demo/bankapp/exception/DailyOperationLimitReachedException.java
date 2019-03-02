package com.demo.bankapp.exception;

public class DailyOperationLimitReachedException extends RuntimeException {

	private static final long serialVersionUID = -6260854119635270900L;

	public DailyOperationLimitReachedException() {
		super("Daily transaction limit is reached.");
	}

}
