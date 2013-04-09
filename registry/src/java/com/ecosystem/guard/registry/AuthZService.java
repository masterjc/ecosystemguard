package com.ecosystem.guard.registry;

import java.io.Writer;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.registry.AuthZRequest;
import com.ecosystem.guard.domain.service.registry.AuthZResponse;
import com.ecosystem.guard.domain.service.registry.AuthZStatus;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.AuthZInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/authz", name = "authz-service")
public class AuthZService extends AuthenticatedService<AuthZRequest, AuthZResponse> {
	public final static String AUTHORIZE_ALL_SERVICES = "*";
	private static final long serialVersionUID = 4690588262956515967L;

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
	protected void execute(AuthenticationContext authnContext, Transaction tx, DaoManager daoManager,
			AuthZRequest request, Writer writer) throws Exception {
		if (request.getHostId() == null || request.getResourceId() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing host/resource information"));
		List<AuthZInfo> authzResources = daoManager.getAuthZInfo(authnContext.getUsername(), request.getHostId());
		if (authzResources == null || authzResources.size() == 0)
			throw new ServiceException(new Result(Status.AUTHZ_ERROR, AuthZStatus.NOT_AUTHORIZED));
		boolean authorized = false;
		for (AuthZInfo authzInfo : authzResources) {
			String resourceId = authzInfo.getResourceId();
			if (resourceId.equals(AUTHORIZE_ALL_SERVICES) || resourceId.equals(request.getResourceId())) {
				authorized = true;
				break;
			}
		}
		if (!authorized)
			throw new ServiceException(new Result(Status.AUTHZ_ERROR, AuthZStatus.NOT_AUTHORIZED));
		AuthZResponse response = new AuthZResponse();
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, AuthZResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass()
	 */
	@Override
	protected Class<AuthZRequest> getRequestJaxbClass() {
		return AuthZRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass()
	 */
	@Override
	protected Class<AuthZResponse> getResponseJaxbClass() {
		return AuthZResponse.class;
	}

}
