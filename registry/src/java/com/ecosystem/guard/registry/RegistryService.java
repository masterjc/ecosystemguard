package com.ecosystem.guard.registry;

import java.io.Reader;
import java.io.Writer;

import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.Deserializer;
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
public class RegistryService extends TransactionalService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.TransactionalService#execute(javax
	 * .persistence.EntityManager, java.io.Writer)
	 */
	@Override
	protected void execute(PersistenceService jpaService, Reader requestReader,
			Writer responseWriter) throws Exception {
		try {
			RegisterRequest request = Deserializer.deserialize(RegisterRequest.class, requestReader);
			jpaService.getAccountInfo(request.getCredentials().getUsernamePassword().getUsername());
			AccountInfo accInfo = new AccountInfo();
			accInfo.setUsername("a@gmail.com");
			accInfo.setPassword("AAAAAAAAAAAA=");
			accInfo.setTelephoneNumber("987676663");
			accInfo.setRecoverMail("otro@gmail.com");
			//jpaService.insert(accInfo);
			responseWriter.write(jpaService.getAccountInfo("a@gmail.com").getTelephoneNumber());
			
		} catch (Exception e) {
			writeAndThrowException(e, responseWriter);
		}
	}

	private void writeAndThrowException(Exception e, Writer writer)
			throws Exception {
		RegisterResponse response = new RegisterResponse();
		Result result = new Result();
		result.setStatus(Result.Status.SERVER_ERROR);
		result.setMessage(e.getMessage());
		response.setResult(result);
		try {
			Serializer.serialize(response, RegisterResponse.class, writer);
		} catch (Exception e1) {
			throw e1;
		}
		throw e;
	}
}
