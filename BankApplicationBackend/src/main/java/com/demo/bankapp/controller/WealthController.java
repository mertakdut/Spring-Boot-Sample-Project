package com.demo.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.assembler.WealthResourceAssembler;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.model.User;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.request.RetrieveWealthRequest;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;

@RestController
@RequestMapping(value = "/wealth", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*")
public class WealthController {

	@Autowired
	private IWealthService wealthService;

	@Autowired
	private IUserService userService;

	@Autowired
	private WealthResourceAssembler assembler;

	@PostMapping("/retrieve")
	public Resource<Wealth> retrieveWealth(@RequestBody RetrieveWealthRequest request) {

		if (request == null) {
			throw new BadRequestException();
		}

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException("Invalid username.");
		}

		User user = userService.findByUserName(request.getUsername());
		Wealth wealth = wealthService.findWealth(user.getId());
		return assembler.toResource(wealth);
	}

}
