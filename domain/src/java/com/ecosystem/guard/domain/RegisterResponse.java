package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterResponse {
	private Result result;
	
	public Result getResult() {
		return result;
	}

	@XmlElement
	public void setResult(Result result) {
		this.result = result;
	}
}
