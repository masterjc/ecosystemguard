package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.UnregisterRequest;
import com.ecosystem.guard.domain.service.UnregisterResponse;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/unregister", name = "unregister-service")
public class UnregisterService extends AuthenticatedService<UnregisterRequest, UnregisterResponse> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1898476863531919901L;

	@Override
	protected void execute(AuthenticationContext authnContext, Transaction transaction, DaoManager daoManager,
			UnregisterRequest request, Writer writer) throws Exception {
		daoManager.deleteAccount(authnContext.getUsername());
		UnregisterResponse response = new UnregisterResponse();
		response.setResult(new Result(Result.Status.OK));
		Serializer.serialize(response, UnregisterResponse.class, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.TransactionalService#getRequestJaxbClass
	 * ()
	 */
	@Override
	protected Class<UnregisterRequest> getRequestJaxbClass() {
		return UnregisterRequest.class;
	}

	@Override
	protected Class<UnregisterResponse> getResponseJaxbClass() {
		return UnregisterResponse.class;
	}
}
