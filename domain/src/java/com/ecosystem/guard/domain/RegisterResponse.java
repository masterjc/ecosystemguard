package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Result.Status;

@XmlRootElement
@XmlSeeAlso(RegisterStatus.class)
public class RegisterResponse extends Response<RegisterStatus> {
	public RegisterResponse() {
	}
	
	public RegisterResponse(Status status, RegisterStatus appStatus, String message ) {
		super(status, appStatus, message);
	}
	
	public RegisterResponse(Status status) {
		super(status);
	}
}
