package com.demo.bankapp.service.abstractions;

import java.util.List;

import com.demo.bankapp.model.User;

public interface IUserService {

	List<User> findAll();
	User findById(Long id);

}
