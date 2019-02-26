package com.demo.bankapp.request;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MakeTransferRequest extends BaseRequest {

	private String senderUsername;
	private String receiverTcno;
	private String currency;
	private BigDecimal amount;

}
