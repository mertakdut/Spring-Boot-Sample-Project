package com.demo.bankapp.response;

import com.demo.bankapp.model.Transaction;

import lombok.Data;

@Data
public class CreateTransactionResponse {

	private Transaction transaction;

}
