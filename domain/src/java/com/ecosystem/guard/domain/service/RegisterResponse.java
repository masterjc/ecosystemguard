package com.ecosystem.guard.domain.service;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

@XmlRootElement
@XmlSeeAlso(RegisterStatus.class)
public class RegisterResponse extends Response {
	
}
