package com.demo.bankapp.exception;

public class TransactionLimitException extends RuntimeException {

	public TransactionLimitException(String message) {
		super("Transaction Limit: " + message);
	}

}
