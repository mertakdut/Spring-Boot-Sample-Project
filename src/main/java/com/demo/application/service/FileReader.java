package com.demo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FileReaderService")
public class FileReader {

	@Autowired(required = false)
	private SourceProvider sourceProvider;

	public void setSourceProvider(SourceProvider sourceProvider) {
		this.sourceProvider = sourceProvider;
	}

	public SourceProvider getSourceProvider() {
		return this.sourceProvider;
	}

	public String getFileContents() {
		return sourceProvider.getSource() + "from FileReader.getFileContents()";
	}

}
