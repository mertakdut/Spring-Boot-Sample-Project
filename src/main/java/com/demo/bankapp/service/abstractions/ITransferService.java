package com.demo.bankapp.service.abstractions;

import java.util.List;

import com.demo.bankapp.model.Transfer;

public interface ITransferService {

	Transfer createNewTransfer(Transfer transfer);
	
	List<Transfer> findAllTransfersFrom24Hours(Long userId);

}
