package com.demo.bankapp.service.concretions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bankapp.model.User;
import com.demo.bankapp.repository.UserRepository;
import com.demo.bankapp.service.abstractions.IUserService;

@Service
//@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public Iterable<User> findAll() {
		return userRepo.findAll();
	}

}
