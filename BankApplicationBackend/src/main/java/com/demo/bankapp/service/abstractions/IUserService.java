package com.demo.bankapp.service.abstractions;

import java.util.List;

import com.demo.bankapp.model.User;

public interface IUserService {

	List<User> findAll();

	User findByUserName(String username);

	User findByTcno(String tcno);

	User createNewUser(User user);
	
	boolean isUsernameExist(String username);
	
	boolean isTcnoExist(String tcno);

}
