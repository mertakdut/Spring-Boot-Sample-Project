package com.demo.bankapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.assembler.UserResourceAssembler;
import com.demo.bankapp.model.User;
import com.demo.bankapp.service.abstractions.IUserService;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private UserResourceAssembler assembler;

	@GetMapping("/findAll")
	public List<Resource<User>> findAll() {
		return userService.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public Resource<User> find(@PathVariable Long id) {
		User user = userService.findById(id);
		return assembler.toResource(user);
	}

}
