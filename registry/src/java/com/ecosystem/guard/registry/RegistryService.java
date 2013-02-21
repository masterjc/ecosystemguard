package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.RegisterRequest;
import com.ecosystem.guard.domain.RegisterResponse;
import com.ecosystem.guard.domain.RegisterStatus;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.engine.servlet.PersistenceService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.dao.AccountInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@WebServlet(value = "/register", name = "register-service")
public class RegistryService extends PersistenceService<RegisterRequest> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.TransactionalService#getRequestJaxbClass()
	 */
	@Override
	protected Class<RegisterRequest> getRequestJaxbClass() {
		return RegisterRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.TransactionalService#execute(com.ecosystem.guard.engine
	 * .dao.PersistenceService, java.lang.Object, java.io.Writer)
	 */
	@Override
	protected void execute(DaoManager daoManager, RegisterRequest request, Writer responseWriter) throws Exception {
		try {
			RegisterRequest regRequest = (RegisterRequest) request;
			if (regRequest.getCredentials() == null || !regRequest.getCredentials().defined())
				throw new Exception("No credentials");

			String username = regRequest.getCredentials().getUsernamePassword().getUsername();
			if (daoManager.getAccountInfo(username) != null)
				throw new Exception("Account exists");

			AccountInfo accInfo = new AccountInfo();
			accInfo.setUsername(username);
			accInfo.setPassword(regRequest.getCredentials().getUsernamePassword().getPassword());
			accInfo.setTelephoneNumber("987676663");
			accInfo.setRecoverMail("otro@gmail.com");
			daoManager.insert(accInfo);

			RegisterResponse response = new RegisterResponse(Status.OK);
			Serializer.serialize(response, RegisterResponse.class, responseWriter);
		}
		catch (Exception e) {
			writeAndThrowServerException(e, responseWriter);
		}
	}

	private void writeAndThrowClientError() throws Exception {

	}

	private void writeAndThrowException(Exception e, Writer writer) throws Exception {
		RegisterResponse response = new RegisterResponse();
		Result result = new Result();
		result.setStatus(Result.Status.SERVER_ERROR);
		result.setMessage(e.getMessage());
		response.setResult(result);
		try {
			Serializer.serialize(response, RegisterResponse.class, writer);
		}
		catch (Exception e1) {
			throw e1;
		}
		throw e;
	}

}
