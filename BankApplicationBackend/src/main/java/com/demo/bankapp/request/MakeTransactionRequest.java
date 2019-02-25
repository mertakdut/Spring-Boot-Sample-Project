package com.demo.bankapp.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MakeTransactionRequest extends BaseRequest {

	private String username;
	private boolean isBuying;
	private String currency;
	private BigDecimal amount;

}
