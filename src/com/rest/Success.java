package com.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "response")
public class Success {
	
	private String success;
	private String failure;

	@XmlElement
	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	@XmlElement
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
