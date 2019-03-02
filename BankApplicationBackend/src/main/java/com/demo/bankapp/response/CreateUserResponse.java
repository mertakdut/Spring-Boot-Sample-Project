package com.demo.bankapp.response;

import com.demo.bankapp.model.User;

import lombok.Data;

@Data
public class CreateUserResponse {
	private String username;
	private String tcno;
}
