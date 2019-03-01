package com.demo.bankapp.controller;

import java.util.List;
import java.util.regex.Pattern;
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
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IWealthService wealthService;

	@Autowired
	private UserResourceAssembler assembler;

	@GetMapping("/find/all")
	public List<Resource<User>> findAll() {
		return userService.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
	}

	@PostMapping("/create")
	public Resource<User> createNewUser(@RequestBody CreateNewUserRequest request) {

		if (request == null) {
			throw new BadRequestException();
		}

		if (request.getUsername() == null || request.getUsername().equals("") || request.getPassword() == null || request.getPassword().equals("")) {
			throw new BadRequestException("Invalid credentials.");
		}

		if (request.getTcno() == null || request.getTcno().length() != 11 || !isNumeric(request.getTcno())) {
			throw new BadRequestException("Invalid TC No.");
		}

		User user = userService.createNewUser(new User(request.getUsername(), request.getPassword(), request.getTcno()));
		wealthService.newWealthRecord(user.getId());

		return assembler.toResource(user);
	}

	private boolean isNumeric(String str) {
		return Pattern.matches("[0-9]+", str);
	}

}
