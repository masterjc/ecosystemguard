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
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.registry.RegisterHostRequest;
import com.ecosystem.guard.domain.service.registry.RegisterHostResponse;
import com.ecosystem.guard.domain.service.registry.RegisterStatus;
import com.ecosystem.guard.engine.SystemProperties;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.HostInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/registerhost", name = "registerhost-service")
public class RegisterHostService extends AuthenticatedService<RegisterHostRequest, RegisterHostResponse> {

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
			RegisterHostRequest request, Writer writer) throws Exception {
		if (request.getHostInformation() == null || request.getHostInformation().getId() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing host information"));
		if (daoManager.getHostInfo(authnContext.getUsername(), request.getHostInformation().getId()) != null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, RegisterStatus.ALREADY_REGISTERED));
		HostInfo daoInfo = new HostInfo();
		daoInfo.setHostId(request.getHostInformation().getId());
		daoInfo.setSummary(request.getHostInformation().getSummary());
		daoInfo.setDescription(request.getHostInformation().getDescription());
		daoInfo.setUsername(authnContext.getUsername());
		daoInfo.setVersion(SystemProperties.getVersion());
		daoManager.insert(daoInfo);
		RegisterHostResponse response = new RegisterHostResponse();
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, RegisterHostResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<RegisterHostRequest> getRequestJaxbClass() {
		return RegisterHostRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<RegisterHostResponse> getResponseJaxbClass() {
		return RegisterHostResponse.class;
	}

}
