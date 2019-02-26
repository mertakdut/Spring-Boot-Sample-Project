package com.demo.bankapp.service.abstractions;

import com.demo.bankapp.model.Transfer;

public interface ITransferService {

	Transfer createNewTransfer(Transfer transfer);

}
