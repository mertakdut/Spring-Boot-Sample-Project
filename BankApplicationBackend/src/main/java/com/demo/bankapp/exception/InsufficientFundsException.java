package com.demo.bankapp.exception;

public class InsufficientFundsException extends RuntimeException {

	public InsufficientFundsException() {
		super("Insufficient Funds: Not enough TRY.");
	}

	public InsufficientFundsException(String currency) {
		super("Insufficient Funds: Your " + currency + " funds are not enough.");
	}

}
