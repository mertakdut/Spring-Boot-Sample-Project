package com.demo.bankapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.assembler.UserResourceAssembler;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.CreateNewUserRequest;
import com.demo.bankapp.request.LoginRequest;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*") // TODO: Replace with FE domain.
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IWealthService wealthService;

	@Autowired
	private UserResourceAssembler assembler;

	@GetMapping("/findAll")
	public List<Resource<User>> findAll() {
		return userService.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
	}

	@PostMapping("/new")
	public Resource<User> createNewUser(@RequestBody CreateNewUserRequest request) {

		if (request == null || request.getUsername() == null || request.getPassword() == null || request.getTcno() == null || request.getUsername().equals("")
				|| request.getPassword().equals("") || request.getTcno().equals("")) {
			throw new BadRequestException();
		}

		User user = userService.addNewUser(request);
		wealthService.newWealthRecord(user.getId());

		return assembler.toResource(user);
	}

	@PostMapping("/login")
	public Resource<User> login(@RequestBody LoginRequest request) {

		if (request == null || request.getUsername() == null || request.getPassword() == null || request.getUsername().equals("") || request.getPassword().equals("")) {
			throw new BadRequestException();
		}

		User user = userService.login(request);
		return assembler.toResource(user);
	}

}
