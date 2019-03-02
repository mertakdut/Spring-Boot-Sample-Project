package com.demo.bankapp.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateUserRequest extends BaseRequest {

	private String username;
	private String password;
	private String tcno;

}
