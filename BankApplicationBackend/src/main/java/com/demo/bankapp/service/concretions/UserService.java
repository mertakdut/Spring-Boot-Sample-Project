package com.demo.bankapp.service.concretions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bankapp.exception.BadCredentialsException;
import com.demo.bankapp.exception.UserNotFoundException;
import com.demo.bankapp.model.User;
import com.demo.bankapp.repository.UserRepository;
import com.demo.bankapp.request.CreateNewUserRequest;
import com.demo.bankapp.request.LoginRequest;
import com.demo.bankapp.service.abstractions.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User addNewUser(CreateNewUserRequest request) {
		User user = new User(request.getUsername(), request.getPassword(), request.getTcno());
		return repository.save(user);
	}

	@Override
	public User login(LoginRequest request) throws UserNotFoundException {

		User user = findByUserName(request.getUsername());

		// TODO: Encoding.
		// TODO: Stop timing attacks.
		if (user.getPassword() == null || !user.getPassword().equals(request.getPassword())) {
			throw new BadCredentialsException();
		}

		return user;
	}

	@Override
	public User findByUserName(String username) {
		User user = repository.findByUsername(username);

		if (user == null)
			throw new UserNotFoundException(username);
		else
			return user;
	}

	@Override
	public User findByTcno(String tcno) {
		User user = repository.findByTcno(tcno);

		if (user == null)
			throw new UserNotFoundException(tcno);
		else
			return user;
	}

}
