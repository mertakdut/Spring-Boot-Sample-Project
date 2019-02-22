package com.demo.application.service;

import org.springframework.stereotype.Service;

@Service("SourceProvider")
public class SourceProvider {

	public String getSource() {
		return "from SourceProvider.getSource() > ";
	}

}
