package com.ecosystem.guard.domain.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DAO for accessing registered host information
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "hostInfo")
public class HostInfo implements Serializable {

	@Id
	@Column(name = "username", nullable = false, length = 254)
	private String username;

	@Id
	@Column(name = "hostId", nullable = false, length = 64)
	private String hostId;

	@Column(name = "summary", nullable = false, length = 256)
	private String summary;

	@Column(name = "version", nullable = false, length = 16)
	private String version;

	@Column(name = "description", nullable = true, length = 1000)
	private String description;

	public HostInfo() {
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
