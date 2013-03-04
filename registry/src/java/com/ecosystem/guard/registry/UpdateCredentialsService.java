/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.UpdateCredentialsRequest;
import com.ecosystem.guard.domain.service.UpdateCredentialsResponse;
import com.ecosystem.guard.engine.PasswordCipher;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.engine.servlet.ServiceException;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.AccountInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/updatecredentials", name = "updatecredentials-service")
public class UpdateCredentialsService extends AuthenticatedService<UpdateCredentialsRequest, UpdateCredentialsResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7619873214692127750L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.AuthenticatedService#execute(com.ecosystem.guard.engine
	 * .authn.AuthenticationContext, com.ecosystem.guard.persistence.Transaction,
	 * com.ecosystem.guard.persistence.DaoManager, com.ecosystem.guard.domain.Request,
	 * java.io.Writer)
	 */
	@Override
	protected void execute(AuthenticationContext authnContext, Transaction transaction, DaoManager daoManager,
			UpdateCredentialsRequest request, Writer writer) throws Exception {
		if( request.getNewPassword() == null )
			throw new ServiceException(new Result(Status.CLIENT_ERROR, "Missing new credentials"));
		AccountInfo accountInfo = daoManager.getAccountInfo(authnContext.getUsername());
		String cipheredPassword = PasswordCipher.getInstance().cipherPassword(request.getNewPassword().getBytes());
		accountInfo.setPassword(cipheredPassword);
		daoManager.update(accountInfo);
		UpdateCredentialsResponse response = new UpdateCredentialsResponse();
		response.setResult(new Result(Result.Status.OK));
		Serializer.serialize(response, UpdateCredentialsResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<UpdateCredentialsRequest> getRequestJaxbClass() {
		return UpdateCredentialsRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<UpdateCredentialsResponse> getResponseJaxbClass() {
		return UpdateCredentialsResponse.class;
	}

}
