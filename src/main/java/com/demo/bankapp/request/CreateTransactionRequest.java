package com.demo.bankapp.request;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateTransactionRequest extends BaseRequest {

	private String username;
	private boolean isBuying;
	private String currency;
	private BigDecimal amount;

}
