package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.TransactionController;
import com.demo.bankapp.model.Transaction;

@Component
public class TransactionResourceAssembler implements ResourceAssembler<Transaction, Resource<Transaction>> {
	
	@Override
	public Resource<Transaction> toResource(Transaction transaction) {
		return new Resource<>(transaction,
			linkTo(methodOn(TransactionController.class).createTransaction(null)).withSelfRel(),
			linkTo(methodOn(TransactionController.class).findAll(null)).withRel("transactions")
		);
	}

}
