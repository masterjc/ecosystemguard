/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.app;

import java.io.Writer;

import com.ecosystem.guard.domain.service.host.TakePictureRequest;
import com.ecosystem.guard.domain.service.host.TakePictureResponse;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.NonTransactionalService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class TakePictureService extends NonTransactionalService<TakePictureRequest, TakePictureResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9135833740206495900L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.NonTransactionalService#execute(com.ecosystem.guard.engine
	 * .authn.AuthenticationContext, com.ecosystem.guard.domain.Request, java.io.Writer)
	 */
	@Override
	protected void execute(AuthenticationContext arg0, TakePictureRequest arg1, Writer arg2) throws Exception {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<TakePictureRequest> getRequestJaxbClass() {
		return TakePictureRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<TakePictureResponse> getResponseJaxbClass() {
		return TakePictureResponse.class;
	}

}
