package com.demo.bankapp.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.assembler.TransactionAssembler;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.MakeTransactionRequest;
import com.demo.bankapp.service.concretions.TransactionService;
import com.demo.bankapp.service.concretions.UserService;
import com.demo.bankapp.service.concretions.UserWealthService;

@RestController
@RequestMapping(value = "/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*") // TODO: Replace with FE domain.
public class TransactionController {

	@Autowired
	TransactionAssembler assembler;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@Autowired
	UserWealthService userWealthService;

	@PostMapping("/make")
	public Resource<Transaction> makeTransaction(@RequestBody MakeTransactionRequest request) {

		if (request == null || request.getUsername() == null || request.getUsername().equals("") || request.getCurrency() == null || request.getCurrency().equals("")
				|| request.getAmount() == BigDecimal.ZERO || request.getCurrency().equals("TRY")) {
			throw new BadRequestException();
		}

		User user = userService.findByUserName(request.getUsername());
		userWealthService.makeWealthTransaction(user.getId(), request.getCurrency(), request.getAmount(), request.isBuying());

		Transaction transaction = transactionService.createNewTransaction(user.getId(), request);
		return assembler.toResource(transaction);
	}

}
