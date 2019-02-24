package com.demo.bankapp.service.abstractions;

import java.util.List;

import com.demo.bankapp.model.User;
import com.demo.bankapp.request.CreateNewUserRequest;
import com.demo.bankapp.request.LoginRequest;

public interface IUserService {

	List<User> findAll();
	
	User addNewUser(CreateNewUserRequest request);
	
	User login(LoginRequest request);

}
