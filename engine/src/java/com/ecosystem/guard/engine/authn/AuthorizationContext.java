package com.ecosystem.guard.engine.authn;

public class AuthorizationContext {
	private AuthenticationContext authnContext;
	private boolean authorized;
	private String resourceId;
	private String hostId;
	
	public AuthenticationContext getAuthnContext() {
		return authnContext;
	}
	
	public void setAuthnContext(AuthenticationContext authnContext) {
		this.authnContext = authnContext;
	}
	
	public boolean isAuthorized() {
		return authorized;
	}
	
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getHostId() {
		return hostId;
	}
	
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
}
