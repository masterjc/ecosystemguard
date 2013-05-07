package com.ecosystem.guard.persistence.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DAO for accessing host Ip information
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ipInfo")
public class IpInfo implements Serializable {
	@Id
	@Column(name = "hostId", nullable = false, length = 64)
	private String hostId;

	@Column(name = "publicIp", nullable = false, length = 39)
	private String publicIp;
	
	@Column(name = "privateIp", nullable = false, length = 39)
	private String privateIp;

	@Column(name = "lastPublicIpChange", nullable = false)
	private java.sql.Date lastChange;

	public IpInfo() {
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	
	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public java.sql.Date getLastPublicIpChange() {
		return lastChange;
	}

	public void setLastPublicIpChange(java.sql.Date lastChange) {
		this.lastChange = lastChange;
	}
}
