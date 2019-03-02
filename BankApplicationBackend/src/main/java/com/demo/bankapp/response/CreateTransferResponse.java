package com.demo.bankapp.response;

import com.demo.bankapp.model.Transfer;

import lombok.Data;

@Data
public class CreateTransferResponse {
	private Transfer transfer;
}
