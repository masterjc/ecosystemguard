package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class Result {
	@XmlType(name = "Status")
	@XmlEnum
	public enum Status {
		@XmlEnumValue("OK")
		OK( "OK" ),
		
		@XmlEnumValue("CLIENT_ERROR")
		CLIENT_ERROR( "CLIENT_ERROR" ),
		
		@XmlEnumValue("SERVER_ERROR")
		SERVER_ERROR( "SERVER_ERROR" ),
		
		@XmlEnumValue("AUTHN_ERROR")
		AUTHN_ERROR( "AUTHN_ERROR" );
		
		private String statusCode;
		
		private Status( String status ) {
			this.statusCode = status;
		}
		
		public String getStatusCode() {
			return statusCode;
		}
	}
	
	private Status status;
	private String appStatus;
	private String message;
	
	public Result() {
	}
	
	public Result( Status status, ServiceStatus appStatus, String message ) {
		this.status = status;
		this.appStatus = appStatus.getRegisterStatusCode();
		this.message = message;
	}
	
	public Result( Status status, String message ) {
		this.status = status;
		this.message = message;
	}
	
	public Result( Status status ) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getAppStatus() {
		return appStatus;
	}
	
	@XmlElement
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@XmlElement
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	public String getMessage() {
		return message;
	}
	
	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}
}
