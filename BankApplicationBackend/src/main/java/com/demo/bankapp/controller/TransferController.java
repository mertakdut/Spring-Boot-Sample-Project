package com.demo.bankapp.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

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
import com.demo.bankapp.exception.TransactionLimitException;
import com.demo.bankapp.model.Transfer;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.MakeTransferRequest;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;
import com.demo.bankapp.service.concretions.TransferService;

@RestController
@RequestMapping(value = "/transfer", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*")
public class TransferController {

	@Autowired
	IUserService userService;

	@Autowired
	IWealthService wealthService;

	@Autowired
	TransferService transferService;

	@Autowired
	TransferResourceAssembler assembler;

	@PostMapping("/create")
	public Resource<Transfer> createTransfer(@RequestBody MakeTransferRequest request) {

		if (request == null || request.getCurrency() == null || request.getCurrency().equals("")) {
			throw new BadRequestException();
		}

		if (request.getSenderUsername() == null || request.getSenderUsername().equals("")) {
			throw new BadRequestException("User not found.");
		}

		if (request.getReceiverTcno() == null || request.getReceiverTcno().equals("")) {
			throw new BadRequestException("Invalid TCNo.");
		}

		if (request.getAmount() == null || request.getAmount().signum() == 0 || request.getAmount().signum() == -1) {
			throw new BadRequestException("Please enter a valid amount.");
		}

		Map<String, Double> currencyRates = wealthService.getCurrencyRates();

		BigDecimal singleTransferLimit = new BigDecimal(20000);
		BigDecimal tryEquivalent = getTryEquivalent(currencyRates, request.getCurrency(), request.getAmount());
		if (tryEquivalent.compareTo(singleTransferLimit) == 1) {
			throw new TransactionLimitException("Exceeded Maximum Value Per Transaction.");
		}

		User senderUser = userService.findByUserName(request.getSenderUsername());

		List<Transfer> last24HourTransfers = transferService.findAllTransfersFrom24Hours(senderUser.getId());
		checkDailyTransferLimitExceedition(currencyRates, last24HourTransfers, tryEquivalent);

		User receiverUser = userService.findByTcno(request.getReceiverTcno());

		if (senderUser.equals(receiverUser)) {
			throw new BadRequestException("You can't send money to yourself.");
		}

		wealthService.makeWealthTransaction(senderUser.getId(), request.getCurrency(), request.getAmount(), false);
		wealthService.makeWealthTransaction(receiverUser.getId(), request.getCurrency(), request.getAmount(), true);

		Transfer transfer = transferService.createNewTransfer(new Transfer(senderUser.getId(), receiverUser.getId(), request.getCurrency(), request.getAmount()));
		return assembler.toResource(transfer);
	}

	private BigDecimal getTryEquivalent(Map<String, Double> currencyRates, String currency, BigDecimal amount) {
		BigDecimal transferCurrRate = BigDecimal.valueOf(currencyRates.get(currency));
		return amount.divide(transferCurrRate, 9, RoundingMode.HALF_UP);
	}

	private void checkDailyTransferLimitExceedition(Map<String, Double> currencyRates, List<Transfer> last24HourTransfers, BigDecimal transferTryEquivalent) {

		BigDecimal dailyTransferLimit = new BigDecimal(100000);

		BigDecimal rate;
		BigDecimal tryEquivalent;
		for (Transfer transfer : last24HourTransfers) {
			rate = BigDecimal.valueOf(currencyRates.get(transfer.getCurrency()));
			tryEquivalent = transfer.getAmount().divide(rate, 9, RoundingMode.HALF_UP);

			transferTryEquivalent = transferTryEquivalent.add(tryEquivalent);
			if (transferTryEquivalent.compareTo(dailyTransferLimit) != -1) {
				throw new TransactionLimitException("Exceeded Maximum Transaction Value For the Day.");
			}
		}

	}

}