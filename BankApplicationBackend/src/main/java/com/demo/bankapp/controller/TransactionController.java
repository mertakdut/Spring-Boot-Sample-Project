package com.demo.bankapp.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.demo.bankapp.request.FindAllByUserRequest;
import com.demo.bankapp.request.MakeTransactionRequest;
import com.demo.bankapp.service.concretions.TransactionService;
import com.demo.bankapp.service.concretions.UserService;
import com.demo.bankapp.service.concretions.WealthService;

@RestController
@RequestMapping(value = "/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*")
public class TransactionController {

	@Autowired
	TransactionResourceAssembler assembler;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@Autowired
	WealthService userWealthService;

	@PostMapping("/create")
	public Resource<Transaction> createTransaction(@RequestBody MakeTransactionRequest request) {

		if (request == null) {
			throw new BadRequestException();
		}

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException("Invalid username.");
		}

		if (request.getCurrency() == null || request.getCurrency().equals("")) {
			throw new BadRequestException("Invalid currency.");
		}

		if (request.getAmount() != null && request.getAmount().signum() == 0 || request.getAmount().signum() == -1) {
			throw new BadRequestException("Invalid amount.");
		}

		if (request.getCurrency().equals("TRY")) {
			throw new BadRequestException("You can't make transactions with TRY.");
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

	@PostMapping("/find/all")
	public List<Resource<Transaction>> findAll(@RequestBody FindAllByUserRequest request) {

		if (request == null) {
			throw new BadRequestException();
		}

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException("Invalid username.");
		}

		User user = userService.findByUserName(request.getUsername());
		return transactionService.findAllByUserId(user.getId()).stream().map(assembler::toResource).collect(Collectors.toList());
	}

}
