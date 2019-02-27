package com.demo.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bankapp.assembler.TransferResourceAssembler;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.model.Transfer;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.MakeTransferRequest;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;
import com.demo.bankapp.service.concretions.TransferService;

@RestController
@RequestMapping(value = "/transfer", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*") // TODO: Replace with FE domain.
public class TransferController {

	@Autowired
	IUserService userService;

	@Autowired
	IWealthService wealthService;

	@Autowired
	TransferService transferService;

	@Autowired
	TransferResourceAssembler assembler;

	@PostMapping("/make")
	public Resource<Transfer> makeTransfer(@RequestBody MakeTransferRequest request) {

		if (request == null || request.getCurrency() == null || request.getCurrency().equals("")) {
			throw new BadRequestException();
		}

		if (request.getSenderUsername() == null || request.getSenderUsername().equals("")) {
			throw new BadRequestException("User not found.");
		}

		if (request.getReceiverTcno() == null || request.getReceiverTcno().equals("")) {
			throw new BadRequestException("Invalid TCNo.");
		}

		if (request.getAmount().signum() == 0 || request.getAmount().signum() == -1) {
			throw new BadRequestException("Please enter a valid amount.");
		}

		User senderUser = userService.findByUserName(request.getSenderUsername());
		User receiverUser = userService.findByTcno(request.getReceiverTcno());

		if (senderUser.equals(receiverUser)) {
			throw new BadRequestException("You can't send money to yourself.");
		}

		wealthService.makeWealthTransaction(senderUser.getId(), request.getCurrency(), request.getAmount(), false);
		wealthService.makeWealthTransaction(receiverUser.getId(), request.getCurrency(), request.getAmount(), true);

		Transfer transfer = transferService.createNewTransfer(new Transfer(senderUser.getId(), receiverUser.getId(), request.getCurrency(), request.getAmount()));
		return assembler.toResource(transfer);
	}

}