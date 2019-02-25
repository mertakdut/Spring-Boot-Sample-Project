package com.demo.bankapp.exception;

public class InsufficientFundsException extends RuntimeException {

	public InsufficientFundsException() {
		super("Insufficient Funds: Not enough TRY.");
	}

	public InsufficientFundsException(String currency) {
		super("InsufficientFunds: Your " + currency + " funds are not enough.");
	}

}
