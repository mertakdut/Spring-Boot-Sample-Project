package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.WealthController;
import com.demo.bankapp.model.Wealth;

@Component
public class WealthResourceAssembler implements ResourceAssembler<Wealth, Resource<Wealth>> {

	@Override
	public Resource<Wealth> toResource(Wealth wealth) {
		return new Resource<>(wealth,
		 linkTo(methodOn(WealthController.class).retrieveWealth(null)).withSelfRel());
	}
}