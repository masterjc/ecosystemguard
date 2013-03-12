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
import java.sql.Date;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.UpdateIpRequest;
import com.ecosystem.guard.domain.service.UpdateIpResponse;
import com.ecosystem.guard.domain.service.UpdateIpStatus;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.engine.servlet.ServiceException;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.IpInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/updateip", name = "updateip-service")
public class UpdateIpService extends AuthenticatedService<UpdateIpRequest, UpdateIpResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7884754975163454386L;

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
			UpdateIpRequest request, Writer writer) throws Exception {
		if (request.getHostId() == null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, "Missing HostId"));
		if (authContext.getRemoteIpAddress() == null)
			throw new ServiceException(new Result(Status.SERVER_ERROR, UpdateIpStatus.IP_NOT_PROVIDED));
		IpInfo ipInfo = daoManager.getIpInfo(request.getHostId());
		boolean updated = false;
		if (ipInfo == null) {
			ipInfo = new IpInfo();
			ipInfo.setHostId(request.getHostId());
			ipInfo.setPublicIp(authContext.getRemoteIpAddress());
			ipInfo.setLastChange(new Date(new java.util.Date().getTime()));
			daoManager.insert(ipInfo);
			updated = true;
		}
		else {
			if (!ipInfo.getPublicIp().equals(authContext.getRemoteIpAddress())) {
				ipInfo.setPublicIp(authContext.getRemoteIpAddress());
				ipInfo.setLastChange(new Date(new java.util.Date().getTime()));
				daoManager.update(ipInfo);
				updated = true;
			}
		}
		UpdateIpResponse response = new UpdateIpResponse();
		response.setResult(new Result(Result.Status.OK));
		response.setUpdated(updated);
		response.setPublicIp(ipInfo.getPublicIp());
		Serializer.serialize(response, UpdateIpResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<UpdateIpRequest> getRequestJaxbClass() {
		return UpdateIpRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<UpdateIpResponse> getResponseJaxbClass() {
		return UpdateIpResponse.class;
	}

}
