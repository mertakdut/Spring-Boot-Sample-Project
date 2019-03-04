package com.demo.bankapp.exception;

public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = 8435355771655372975L;

	public InsufficientFundsException() {
		super("Insufficient Funds: Not enough TRY.");
	}

	public InsufficientFundsException(String currency) {
		super("Insufficient Funds: Your " + currency + " funds are not enough.");
	}

}
