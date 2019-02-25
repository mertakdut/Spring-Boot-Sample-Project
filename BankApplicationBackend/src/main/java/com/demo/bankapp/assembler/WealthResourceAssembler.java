package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.UserController;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.request.CreateNewUserRequest;

@Component
public class WealthResourceAssembler implements ResourceAssembler<Wealth, Resource<Wealth>> {

	@Override
	public Resource<Wealth> toResource(Wealth user) {

		// CreateNewUserRequest cnuRequest = new CreateNewUserRequest();
		// cnuRequest.setUsername(user.getUsername());
		// cnuRequest.setTcno(user.getTcno());
		// cnuRequest.setPassword(user.getPassword());

		return new Resource<>(user
		// linkTo(methodOn(UserController.class).createNewUser(cnuRequest)).withSelfRel(),
		// linkTo(methodOn(UserController.class).findAll()).withRel("users")
		);
	}
}