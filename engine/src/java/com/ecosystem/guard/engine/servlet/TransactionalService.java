/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.engine.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public abstract class TransactionalService extends HttpServlet {
	
	@PersistenceUnit(name="EcosystemGuard")
	private EntityManagerFactory entityManagerFactory;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException( "EcosystemGuard services do not support HTTP GET method" );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		try {
			execute(entityManager, response.getWriter());
			entityManager.getTransaction().commit();
		} catch( Exception e ) {
			entityManager.getTransaction().rollback();
			throw new ServletException("Error executing service operation", e );
		} finally {
			response.getWriter().flush();
		}
	}
	
	protected abstract void execute(EntityManager entityManager, Writer responseWriter) throws Exception;
	
	
}
