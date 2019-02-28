package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.UserController;
import com.demo.bankapp.model.User;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

	@Override
	public Resource<User> toResource(User user) {
		return new Resource<>(user,
				linkTo(methodOn(UserController.class).createNewUser(null)).withSelfRel(),
				linkTo(methodOn(UserController.class).findAll()).withRel("users"),
				linkTo(methodOn(UserController.class).login(null)).withRel("login"));
	}
}