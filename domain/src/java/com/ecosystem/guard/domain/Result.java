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
	private String message;
	
	public Status getStatus() {
		return status;
	}
	
	@XmlElement
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}
}
