package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.TransferController;
import com.demo.bankapp.model.Transfer;
import com.demo.bankapp.request.MakeTransferRequest;

@Component
public class TransferResourceAssembler implements ResourceAssembler<Transfer, Resource<Transfer>> {

	@Override
	public Resource<Transfer> toResource(Transfer transfer) {

		MakeTransferRequest mtRequest = new MakeTransferRequest();
		mtRequest.setSenderUsername(transfer.getFromUserId().toString());
		mtRequest.setReceiverTcno(transfer.getToUserId().toString());
		mtRequest.setCurrency(transfer.getCurrency());
		mtRequest.setAmount(transfer.getAmount());

		return new Resource<>(transfer, linkTo(methodOn(TransferController.class).makeTransfer(mtRequest)).withSelfRel()
		// linkTo(methodOn(TransactionController.class).findAll()).withRel("transactions")
		);
	}

}
