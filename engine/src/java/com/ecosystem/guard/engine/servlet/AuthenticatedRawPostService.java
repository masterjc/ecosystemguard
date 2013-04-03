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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Request;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.exceptions.DeserializerException;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.authn.AuthenticationService;
import com.ecosystem.guard.logging.EcosystemGuardLogger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public abstract class AuthenticatedRawPostService<T extends Request> extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
			T requestObj = Deserializer.deserialize(getRequestJaxbClass(), request.getReader());
			if (requestObj.getCredentials() == null || !requestObj.getCredentials().defined())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Missing credentials"));
			AuthenticationContext authContext = AuthenticationService
					.authenticate(request, requestObj.getCredentials());
			if (!authContext.isAuthenticated())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Not authenticated"));
			execute(authContext, requestObj, response);
		}
		catch (DeserializerException dEx) {
			EcosystemGuardLogger.logError(dEx, this.getClass());
			writeErrorResponse(new Result(Status.CLIENT_ERROR, dEx.getMessage()), HttpServletResponse.SC_BAD_REQUEST,
					response);
		}
		catch (ServiceException sEx) {
			EcosystemGuardLogger.logError(sEx, this.getClass());
			writeErrorResponse(sEx.getResult(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, this.getClass());
			writeErrorResponse(new Result(Status.SERVER_ERROR, e.getMessage()),
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
		}
	}

	private void writeErrorResponse(Result result, int httpStatus, HttpServletResponse response) throws IOException {
		try {
			response.setStatus(httpStatus);
			Serializer.serialize(result, Result.class, response.getWriter());
		}
		catch (Exception e) {
			throw new IOException("AuthenticatedRawPostService::writeErrorResponse() error", e);
		}
	} 

	/**
	 * Método para la ejecución de la operación del servlet. IMPORTANTE: La respuesta es obligación
	 * del método execute() pero en caso de error deberá
	 * 
	 * @param authContext
	 * @param request
	 * @param httpResponse
	 * @throws Exception
	 */
	protected abstract void execute(AuthenticationContext authContext, T request, HttpServletResponse httpResponse)
			throws Exception;

	/**
	 * Devuelve la clase Java que modela el XML de la peticion mediante Jaxb
	 * 
	 * @return
	 */
	protected abstract Class<T> getRequestJaxbClass();
}
