package com.ecosystem.guard.persistence.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "authzInfo")
public class AuthZInfo implements Serializable {

	@Id
	@Column(name = "username", nullable = false, length = 254)
	private String username;

	@Id
	@Column(name = "hostId", nullable = false, length = 64)
	private String hostId;

	@Column(name = "resourceId", nullable = false, length = 256)
	private String resourceId;

	public AuthZInfo() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
