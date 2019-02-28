package com.demo.bankapp.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.demo.bankapp.controller.TransferController;
import com.demo.bankapp.model.Transfer;

@Component
public class TransferResourceAssembler implements ResourceAssembler<Transfer, Resource<Transfer>> {

	@Override
	public Resource<Transfer> toResource(Transfer transfer) {
		return new Resource<>(transfer, linkTo(methodOn(TransferController.class).createTransfer(null)).withSelfRel());
	}

}
