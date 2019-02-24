package com.demo.bankapp.service.abstractions;

import com.demo.bankapp.model.User;

public interface IUserService {

	Iterable<User> findAll();

}
