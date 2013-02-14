package com.ecosystem.guard.registry;

import java.io.Writer;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

import com.ecosystem.guard.domain.dao.AccountInfo;
import com.ecosystem.guard.engine.servlet.TransactionalService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@WebServlet(value = "/service", name = "registry-servlet")
public class RegistryService extends TransactionalService {

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.engine.servlet.TransactionalService#execute(javax.persistence.EntityManager, java.io.Writer)
	 */
	@Override
	protected void execute(EntityManager entityManager, Writer responseWriter) throws Exception {
		AccountInfo accInfo = new AccountInfo();
		accInfo.setUsername("jc@gmail.com");
		accInfo.setPassword("AAAAAAAAAAAA=");
		accInfo.setTelephoneNumber("987676663");
		accInfo.setRecoverMail("otro@gmail.com");
		entityManager.persist(accInfo);
		responseWriter.write("Insertado correctamente");
	}
}
