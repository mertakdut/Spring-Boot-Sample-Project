package com.demo.bankapp.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindAllTransactionsByUserRequest extends BaseRequest {

	private String username;

}
