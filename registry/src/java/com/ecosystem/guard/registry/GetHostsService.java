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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.GetHostsRequest;
import com.ecosystem.guard.domain.service.GetHostsResponse;
import com.ecosystem.guard.domain.service.GetHostsStatus;
import com.ecosystem.guard.domain.service.HostInformation;
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
@WebServlet(value = "/gethosts", name = "gethosts-service")
public class GetHostsService extends AuthenticatedService<GetHostsRequest, GetHostsResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1809342566301599898L;

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
			GetHostsRequest request, Writer writer) throws Exception {
		List<HostInfo> dbHosts = daoManager.getHostsInfo(authnContext.getUsername());
		if (dbHosts == null || dbHosts.size() == 0)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, GetHostsStatus.NO_REGISTERED_HOSTS));
		List<HostInformation> hosts = new ArrayList<HostInformation>();
		for (HostInfo hostInfo : dbHosts) {
			HostInformation info = new HostInformation();
			info.setId(hostInfo.getHostId());
			info.setSummary(hostInfo.getSummary());
			info.setDescription(hostInfo.getDescription());
			hosts.add(info);
		}
		GetHostsResponse response = new GetHostsResponse();
		response.setHosts(hosts);
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, GetHostsResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<GetHostsRequest> getRequestJaxbClass() {
		return GetHostsRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<GetHostsResponse> getResponseJaxbClass() {
		return GetHostsResponse.class;
	}

}
