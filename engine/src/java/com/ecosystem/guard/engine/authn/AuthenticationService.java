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

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.persistence.DaoManager;


/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AuthenticationService {
	private DaoManager daoManager;
	
	public AuthenticationService(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
	
	public AuthenticationContext authenticate(Credentials credentials) throws Exception {
		AuthenticationContext authnContext = new AuthenticationContext();
		authnContext.setAuthenticated(true);
		return authnContext;
	}
	
	
}
