/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.engine.authn;


/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AuthenticationContext {
	private boolean authenticated = false;
	private String username;
	private String remoteIpAddress;
	
	AuthenticationContext() {
	}
	
	void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}
	
}
