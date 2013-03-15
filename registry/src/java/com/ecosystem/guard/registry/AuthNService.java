package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.registry.AuthNRequest;
import com.ecosystem.guard.domain.service.registry.AuthNResponse;
import com.ecosystem.guard.domain.service.registry.AuthNStatus;
import com.ecosystem.guard.engine.PasswordCipher;
import com.ecosystem.guard.engine.servlet.AnonymousService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.AccountInfo;

@WebServlet(value = "/authn", name = "authn-service")
public class AuthNService extends AnonymousService<AuthNRequest, AuthNResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801670782848166032L;

	@Override
	protected void execute(Transaction transaction, DaoManager daoManager, AuthNRequest request, Writer writer)
			throws Exception {
		if (request.getCredentials() == null || request.getCredentials().getUsernamePassword() == null
				|| request.getCredentials().getUsernamePassword().getUsername() == null
				|| request.getCredentials().getUsernamePassword().getPassword() == null)
			throw new ServiceException(new Result(Status.AUTHN_ERROR, AuthNStatus.MISSING_CREDENTIALS));
		String username = request.getCredentials().getUsernamePassword().getUsername();
		byte[] password = request.getCredentials().getUsernamePassword().getPassword().getBytes();
		AccountInfo accountInfo = daoManager.getAccountInfo(username);
		if (accountInfo == null)
			throw new ServiceException(new Result(Status.AUTHN_ERROR, AuthNStatus.NOT_AUTHENTICATED));
		String registeredPassword = accountInfo.getPassword();
		if (!PasswordCipher.getInstance().checkPasswords(registeredPassword, password))
			throw new ServiceException(new Result(Status.AUTHN_ERROR, AuthNStatus.NOT_AUTHENTICATED));
		AuthNResponse response = new AuthNResponse();
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, AuthNResponse.class, writer);
	}

	@Override
	protected Class<AuthNRequest> getRequestJaxbClass() {
		return AuthNRequest.class;
	}

	@Override
	protected Class<AuthNResponse> getResponseJaxbClass() {
		return AuthNResponse.class;
	}

}
