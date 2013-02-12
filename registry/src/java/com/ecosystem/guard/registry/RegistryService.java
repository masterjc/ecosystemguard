package com.ecosystem.guard.registry;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecosystem.guard.domain.dao.AccountInfo;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@WebServlet(value = "/service", name = "registry-servlet")
public class RegistryService extends HttpServlet {
	
	@PersistenceUnit(name="EcosystemGuard")
	private EntityManagerFactory entityManagerFactory;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			AccountInfo accInfo = new AccountInfo();
			accInfo.setUsername("jc@gmail.com");
			accInfo.setPassword("AAAAAAAAAAAA=");
			accInfo.setTelephoneNumber("987676663");
			accInfo.setRecoverMail("otro@gmail.com");
			entityManager.persist(accInfo);
			entityManager.getTransaction().commit();
			response.getWriter().write("CORRECTO");
		} catch( Exception e ) {
			entityManager.getTransaction().rollback();
			e.printStackTrace(response.getWriter());
		}
		response.getWriter().flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
	}
}
