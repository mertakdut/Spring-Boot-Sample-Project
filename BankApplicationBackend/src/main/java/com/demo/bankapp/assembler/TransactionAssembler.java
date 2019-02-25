package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.TransactionController;
import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.MakeTransactionRequest;

@Component
public class TransactionAssembler implements ResourceAssembler<Transaction, Resource<Transaction>> {
	
	@Override
	public Resource<Transaction> toResource(Transaction transaction) {
		
		MakeTransactionRequest mtRequest = new MakeTransactionRequest();
		
		return new Resource<>(transaction,
			linkTo(methodOn(TransactionController.class).makeTransaction(mtRequest)).withSelfRel()
//			linkTo(methodOn(TransactionController.class).findAll()).withRel("transactions")
		);
	}

}
