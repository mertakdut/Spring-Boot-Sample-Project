package com.demo.bankapp.service.concretions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bankapp.model.Transfer;
import com.demo.bankapp.repository.TransferRepository;
import com.demo.bankapp.service.abstractions.ITransferService;

@Service
public class TransferService implements ITransferService {

	@Autowired
	TransferRepository repository;

	@Override
	public Transfer createNewTransfer(Transfer transfer) {
		return repository.save(transfer);
	}

}
