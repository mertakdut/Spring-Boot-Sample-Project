package com.demo.bankapp.response;

import java.math.BigDecimal;

import com.demo.bankapp.model.Transaction;

import lombok.Data;

@Data
public class CreateTransactionResponse {

	private Transaction transaction;

}
