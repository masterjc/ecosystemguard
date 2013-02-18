package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.RegisterRequest;
import com.ecosystem.guard.domain.RegisterResponse;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.engine.dao.AccountInfo;
import com.ecosystem.guard.engine.dao.PersistenceService;
import com.ecosystem.guard.engine.servlet.TransactionalService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@WebServlet(value = "/register", name = "register-service")
public class RegistryService extends TransactionalService<RegisterRequest> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.TransactionalService#getRequestJaxbClass()
	 */
	@Override
	protected Class<RegisterRequest> getRequestJaxbClass() {
		return RegisterRequest.class;
	}

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.engine.servlet.TransactionalService#execute(com.ecosystem.guard.engine.dao.PersistenceService, java.lang.Object, java.io.Writer)
	 */
	@Override
	protected void execute(PersistenceService persistenceService, RegisterRequest request, Writer responseWriter)
			throws Exception {
		try {
			RegisterRequest regRequest = (RegisterRequest) request;
			persistenceService.getAccountInfo(regRequest.getCredentials().getUsernamePassword().getUsername());
			AccountInfo accInfo = new AccountInfo();
			accInfo.setUsername("a@gmail.com");
			accInfo.setPassword("AAAAAAAAAAAA=");
			accInfo.setTelephoneNumber("987676663");
			accInfo.setRecoverMail("otro@gmail.com");
			// jpaService.insert(accInfo);
			responseWriter.write(persistenceService.getAccountInfo("a@gmail.com").getTelephoneNumber());
		}
		catch (Exception e) {
			writeAndThrowException(e, responseWriter);
		}
		
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
