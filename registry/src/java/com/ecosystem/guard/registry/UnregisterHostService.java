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
import com.ecosystem.guard.domain.service.UnregisterHostRequest;
import com.ecosystem.guard.domain.service.UnregisterHostResponse;
import com.ecosystem.guard.domain.service.UnregisterHostStatus;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.engine.servlet.ServiceException;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.HostInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/unregisterhost", name = "registerhost-service")
public class UnregisterHostService extends AuthenticatedService<UnregisterHostRequest, UnregisterHostResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3648887587603810905L;

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
			UnregisterHostRequest request, Writer writer) throws Exception {
		if (request.getHostInformation() == null || request.getHostInformation().getId() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing host information"));
		HostInfo hostInfo = daoManager.getHostInfo(authnContext.getUsername(), request.getHostInformation().getId());
		if (hostInfo == null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, UnregisterHostStatus.NOT_ASSOCIATED_HOST));
		daoManager.delete(hostInfo);
		UnregisterHostResponse response = new UnregisterHostResponse();
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, UnregisterHostResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<UnregisterHostRequest> getRequestJaxbClass() {
		return UnregisterHostRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<UnregisterHostResponse> getResponseJaxbClass() {
		return UnregisterHostResponse.class;
	}

}