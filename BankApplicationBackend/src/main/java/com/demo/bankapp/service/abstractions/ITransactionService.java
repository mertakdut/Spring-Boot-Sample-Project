package com.demo.bankapp.service.abstractions;

import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.request.MakeTransactionRequest;

public interface ITransactionService {

	Transaction createNewTransaction(Long userId, MakeTransactionRequest request);

}
