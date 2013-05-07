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

import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.registry.GetIpRequest;
import com.ecosystem.guard.domain.service.registry.GetIpResponse;
import com.ecosystem.guard.domain.service.registry.GetIpStatus;
import com.ecosystem.guard.domain.service.registry.IpInformation;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.IpInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/getip", name = "getip-service")
public class GetIpService extends AuthenticatedService<GetIpRequest, GetIpResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5015877022387040042L;

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
	protected void execute(AuthenticationContext authContext, Transaction transaction, DaoManager daoManager,
			GetIpRequest request, Writer writer) throws Exception {
		if (request.getHostId() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing host information"));
		IpInfo ipInfo = daoManager.getIpInfo(request.getHostId());
		if (ipInfo == null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, GetIpStatus.IP_NOT_REGISTERED));
		GetIpResponse response = new GetIpResponse();
		response.setResult(new Result(Result.Status.OK));
		IpInformation ipInformation = new IpInformation();
		ipInformation.setLastPublicIpChange(ipInfo.getLastPublicIpChange());
		ipInformation.setPublicIp(ipInfo.getPublicIp());
		ipInformation.setPrivateIp(ipInfo.getPrivateIp());
		response.setIpInformation(ipInformation);
		Serializer.serialize(response, GetIpResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<GetIpRequest> getRequestJaxbClass() {
		return GetIpRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<GetIpResponse> getResponseJaxbClass() {
		return GetIpResponse.class;
	}

}
