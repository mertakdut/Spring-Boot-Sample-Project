package com.demo.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.configuration.Constants;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.DailyOperationLimitReachedException;
import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.CreateTransactionRequest;
import com.demo.bankapp.request.FindAllTransactionsByUserRequest;
import com.demo.bankapp.response.CreateTransactionResponse;
import com.demo.bankapp.response.FindAllTransactionsByUserResponse;
import com.demo.bankapp.service.abstractions.ITransactionService;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;

@RestController
@RequestMapping(value="/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TransactionController {

	private ITransactionService transactionService;
	private IUserService userService;
	private IWealthService wealthService;

	@Autowired
	public TransactionController(ITransactionService transactionService, IUserService userService, IWealthService wealthService) {
		this.transactionService = transactionService;
		this.userService = userService;
		this.wealthService = wealthService;
	}

	@PostMapping("/create")
	public CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest request) {

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDUSERNAME);
		} else if (request.getCurrency() == null || request.getCurrency().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDCURRENCY);
		} else if (request.getAmount() == null || request.getAmount().signum() == 0 || request.getAmount().signum() == -1) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDAMOUNT);
		} else if (request.getCurrency().equals(Constants.MAIN_CURRENCY)) {
			throw new BadRequestException(Constants.MESSAGE_EXCHANGESWITHMAINCURRENCY);
		}

		User user = userService.findByUserName(request.getUsername());

		int last24HoursOperationCount = transactionService.getOperationCountFromLast24Hours(user.getId());
		if (last24HoursOperationCount >= 10) {
			throw new DailyOperationLimitReachedException();
		}

		wealthService.makeWealthExchange(user.getId(), request.getCurrency(), request.getAmount(), request.isBuying());
		Transaction transaction = transactionService.createNewTransaction(user.getId(), request.isBuying(), request.getCurrency(), request.getAmount());

		CreateTransactionResponse response = new CreateTransactionResponse();
		response.setTransaction(transaction);
		return response;
	}

	@PostMapping("/find/all")
	public FindAllTransactionsByUserResponse findAll(@RequestBody FindAllTransactionsByUserRequest request) {

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDUSERNAME);
		}

		User user = userService.findByUserName(request.getUsername());
		List<Transaction> transactionList = transactionService.findAllByUserId(user.getId());

		FindAllTransactionsByUserResponse response = new FindAllTransactionsByUserResponse();
		response.setTransactionList(transactionList);
		return response;
	}

}
