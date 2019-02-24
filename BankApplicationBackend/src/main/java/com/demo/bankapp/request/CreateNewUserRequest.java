package com.demo.bankapp.request;

import lombok.Data;

@Data
public class CreateNewUserRequest extends BaseRequest {
	
	private String username;
	private String password;
	private String tcno;

}
