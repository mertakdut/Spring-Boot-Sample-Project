package com.demo.application.service;

import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	public String getUser() {
		return "Mert";
	}

}
