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
	
	AuthenticationContext() {
		
	}
	
	void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
}
