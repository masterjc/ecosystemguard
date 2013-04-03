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
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.exceptions.DeserializerException;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.authn.AuthenticationService;
import com.ecosystem.guard.logging.EcosystemGuardLogger;
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
public abstract class AuthenticatedService<T extends Request, R extends Response> extends PersistenceHttpServlet {
	private TransactionFactory transactionFactory = new JpaTransactionFactory();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException(this.getClass().getName() + " service do not support HTTP GET method");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Transaction transaction = null;
		try {
			T requestObj = Deserializer.deserialize(getRequestJaxbClass(), request.getReader());
			EntityManager entityManager = createEntityManager();
			transaction = transactionFactory.createTransaction(entityManager);
			transaction.beginTransaction();
			if (requestObj.getCredentials() == null || !requestObj.getCredentials().defined())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Missing credentials"));
			AuthenticationContext authContext = AuthenticationService
					.authenticate(request, requestObj.getCredentials());
			if (!authContext.isAuthenticated())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Not authenticated"));
			DaoManager daoManager = new DaoManager(entityManager);
			execute(authContext, transaction, daoManager, requestObj, response.getWriter());
			transaction.commitTransaction();
		}
		catch (DeserializerException dEx) {
			EcosystemGuardLogger.logError(dEx, this.getClass());
			rollback(transaction);
			writeErrorResponse(new Result(Status.CLIENT_ERROR, dEx.getMessage()), response.getWriter());
		}
		catch (ServiceException sEx) {
			EcosystemGuardLogger.logError(sEx, this.getClass());
			rollback(transaction);
			writeErrorResponse(sEx.getResult(), response.getWriter());
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, this.getClass());
			rollback(transaction);
			writeErrorResponse(new Result(Status.SERVER_ERROR, e.getMessage()), response.getWriter());
		}
	}

	/**
	 * Rollback de la transacción en curso por algún error en la operación
	 * 
	 * @param transaction
	 */
	private void rollback(Transaction transaction) {
		if (transaction != null) {
			transaction.rollbackTransaction();
		}
	}

	private void writeErrorResponse(Result result, Writer writer) throws IOException {
		try {
			R clientResponse = getResponseJaxbClass().newInstance();
			clientResponse.setResult(result);
			Serializer.serialize(clientResponse, getResponseJaxbClass(), writer);
		}
		catch (Exception e) {
			throw new IOException("AuthenticatedPersistenceService::writeErrorResponse() error", e);
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
	protected abstract void execute(AuthenticationContext authContext, Transaction transaction,
			DaoManager persistenceService, T request, Writer responseWriter) throws Exception;

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
