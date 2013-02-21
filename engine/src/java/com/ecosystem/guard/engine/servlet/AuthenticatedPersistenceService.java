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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Request;
import com.ecosystem.guard.domain.Response;
import com.ecosystem.guard.domain.exceptions.DeserializerException;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.authn.AuthenticationService;
import com.ecosystem.guard.persistence.DaoManager;
import com.ecosystem.guard.persistence.JpaTransactionFactory;
import com.ecosystem.guard.persistence.PersistenceHttpServlet;
import com.ecosystem.guard.persistence.Transaction;
import com.ecosystem.guard.persistence.TransactionFactory;

/**
 * Clase base para Servlets con acceso a la base de datos de EcosystemGuard. Las operaciones sobre
 * la base de datos son transaccionales. El tipo genérico indica la clase JAXB que modela la
 * petición XML.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public abstract class AuthenticatedPersistenceService<T extends Request,R extends Response<?>> extends PersistenceHttpServlet {
	private TransactionFactory transactionFactory = new JpaTransactionFactory();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("EcosystemGuard services do not support HTTP GET method");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		//deserializeRequest();
		T requestObj = Deserializer.deserialize(getRequestJaxbClass(), request.getReader());
		if( requestObj.getCredentials() == null || !requestObj.getCredentials().defined())
			throw new Exception("TODO: no credentials found");
		
		//authenticateRequest();
		EntityManager entityManager = createEntityManager();
		Transaction transaction = null;
		try {
		transaction = transactionFactory.createTransaction(entityManager);
		DaoManager daoManager = new DaoManager(entityManager);
		entityManager.getTransaction().begin();
		AuthenticationService authenticationService = new AuthenticationService(daoManager);
		
			
			
			AuthenticationContext authContext = authenticationService.authenticate(requestObj.getCredentials());
			if( !authContext.isAuthenticated() )
				throw new Exception("TODO: not authenticated");
			
			execute(daoManager, requestObj, response.getWriter());
			entityManager.getTransaction().commit();
		}
		catch (DeserializerException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ex.printStackTrace();
		}
		catch (Exception e) {
			entityManager.getTransaction().rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		finally {
			response.getWriter().flush();
		}
	}

	/**
	 * Método para la ejecución de la operación del servlet. Recibe el @see DaoManager contra la
	 * base de datos de forma transaccional. Recibe un writer donde escribir la respuesta.
	 * IMPORTANTE: La respuesta es obligación del método execute() pero en caso de error deberá
	 * lanzar excepción para que ENGINE sepa tirar atrás la transaccion
	 * 
	 * @param request Objeto que contiene la peticion deserializada de XML a clase Java.
	 * @param entityManager
	 * @param responseWriter
	 * @throws Exception
	 */
	protected abstract void execute(DaoManager persistenceService, T request, Writer responseWriter) throws Exception;

	/**
	 * Devuelve la clase Java que modela el XML de la peticion mediante Jaxb
	 * 
	 * @return
	 */
	protected abstract Class<T> getRequestJaxbClass();
	
	/**
	 * Devuelve la clase Java que modela el XML de la respuesta mediante Jaxb
	 * 
	 * @return
	 */
	protected abstract Class<R> getResponseJaxbClass();

}
