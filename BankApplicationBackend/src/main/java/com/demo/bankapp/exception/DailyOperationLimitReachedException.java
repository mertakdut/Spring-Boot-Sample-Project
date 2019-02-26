package com.demo.bankapp.exception;

public class DailyOperationLimitReachedException extends RuntimeException {

	public DailyOperationLimitReachedException() {
		super("Daily transaction limit is reached.");
	}

}
