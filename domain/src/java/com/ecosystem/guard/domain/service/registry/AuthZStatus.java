package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import com.ecosystem.guard.domain.ServiceStatus;

@XmlType(name = "AppStatus")
@XmlEnum
public enum AuthZStatus implements ServiceStatus {
	@XmlEnumValue("NOT_AUTHORIZED")
	NOT_AUTHORIZED("NOT_AUTHORIZED");

	private String statusCode;

	private AuthZStatus(String status) {
		this.statusCode = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

}
