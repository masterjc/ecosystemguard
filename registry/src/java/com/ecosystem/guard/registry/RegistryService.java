package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.RegisterRequest;
import com.ecosystem.guard.domain.service.RegisterResponse;
import com.ecosystem.guard.domain.service.RegisterStatus;
import com.ecosystem.guard.engine.servlet.AnonymousService;
import com.ecosystem.guard.engine.servlet.ServiceException;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.dao.AccountInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@WebServlet(value = "/register", name = "register-service")
public class RegistryService extends AnonymousService<RegisterRequest, RegisterResponse> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1898476863531919901L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.TransactionalService#execute(com
	 * .ecosystem .guard.engine .dao.PersistenceService, java.lang.Object,
	 * java.io.Writer)
	 */
	@Override
	protected void execute(Transaction transaction, DaoManager daoManager, RegisterRequest request,
			Writer responseWriter) throws Exception {
		String username = request.getCredentials().getUsernamePassword().getUsername();
		if (daoManager.getAccountInfo(username) != null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, RegisterStatus.ALREADY_REGISTERED));
		if (request.getAccountInformation() == null)
			throw new ServiceException(new Result(Status.CLIENT_ERROR, RegisterStatus.MISSING_ACCOUNTINFO));

		AccountInfo accInfo = new AccountInfo();
		accInfo.setUsername(username);
		accInfo.setPassword(request.getCredentials().getUsernamePassword().getPassword());
		accInfo.setTelephoneNumber(request.getAccountInformation().getTelephoneNumber());
		accInfo.setRecoverMail(request.getAccountInformation().getRecoverMail());
		daoManager.insert(accInfo);

		RegisterResponse response = new RegisterResponse();
		response.setResult(new Result(Status.OK));
		Serializer.serialize(response, RegisterResponse.class, responseWriter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.TransactionalService#getRequestJaxbClass
	 * ()
	 */
	@Override
	protected Class<RegisterRequest> getRequestJaxbClass() {
		return RegisterRequest.class;
	}

	@Override
	protected Class<RegisterResponse> getResponseJaxbClass() {
		return RegisterResponse.class;
	}
}
