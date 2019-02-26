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

import com.demo.bankapp.assembler.TransactionResourceAssembler;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.DailyOperationLimitReachedException;
import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.MakeTransactionRequest;
import com.demo.bankapp.service.concretions.TransactionService;
import com.demo.bankapp.service.concretions.UserService;
import com.demo.bankapp.service.concretions.WealthService;

@RestController
@RequestMapping(value = "/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*") // TODO: Replace with FE domain.
public class TransactionController {

	@Autowired
	TransactionResourceAssembler assembler;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@Autowired
	WealthService userWealthService;

	@PostMapping("/make")
	public Resource<Transaction> makeTransaction(@RequestBody MakeTransactionRequest request) {

		if (request == null || request.getUsername() == null || request.getUsername().equals("") || request.getCurrency() == null || request.getCurrency().equals("")
				|| request.getAmount().equals(BigDecimal.ZERO) || request.getCurrency().equals("TRY")) {
			throw new BadRequestException();
		}

		User user = userService.findByUserName(request.getUsername());

		int last24HoursOperationCount = transactionService.getOperationCountFromLast24Hours(user.getId());

		if (last24HoursOperationCount >= 10) {
			throw new DailyOperationLimitReachedException();
		}

		userWealthService.makeWealthExchange(user.getId(), request.getCurrency(), request.getAmount(), request.isBuying());
		Transaction transaction = transactionService.createNewTransaction(user.getId(), request.isBuying(), request.getCurrency(), request.getAmount());

		return assembler.toResource(transaction);
	}

}
