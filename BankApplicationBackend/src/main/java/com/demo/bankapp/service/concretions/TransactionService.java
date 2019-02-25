package com.demo.bankapp.service.concretions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.repository.TransactionRepository;
import com.demo.bankapp.request.MakeTransactionRequest;
import com.demo.bankapp.service.abstractions.ITransactionService;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	TransactionRepository repository;

	@Override
	public Transaction createNewTransaction(Long userId, MakeTransactionRequest request) {
		Transaction transaction = new Transaction(userId, request.isBuying(), request.getCurrency(), request.getAmount());
		return repository.save(transaction);
	}

}
