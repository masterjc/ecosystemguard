package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import com.ecosystem.guard.domain.ServiceStatus;

@XmlType(name = "AppStatus")
@XmlEnum
public enum AuthNStatus implements ServiceStatus {
	@XmlEnumValue("MISSING_CREDENTIALS")
	MISSING_CREDENTIALS("MISSING_CREDENTIALS"),

	@XmlEnumValue("NOT_AUTHENTICATED")
	NOT_AUTHENTICATED("NOT_AUTHENTICATED");

	private String statusCode;

	private AuthNStatus(String status) {
		this.statusCode = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

}
