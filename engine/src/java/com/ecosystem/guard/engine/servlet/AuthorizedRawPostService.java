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
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecosystem.guard.common.Deserializer;
import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.Request;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.exceptions.DeserializerException;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.engine.authn.AuthorizationContext;
import com.ecosystem.guard.engine.authn.AuthorizationService;
import com.ecosystem.guard.logging.EcosystemGuardLogger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public abstract class AuthorizedRawPostService<T extends Request> extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		OutputStream outputStream = response.getOutputStream();
		try {
			T requestObj = Deserializer.deserialize(getRequestJaxbClass(), request.getReader());
			if (requestObj.getCredentials() == null || !requestObj.getCredentials().defined())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Missing credentials"));
			AuthorizationContext authContext = AuthorizationService.authorize(request, requestObj.getCredentials(),
					this.getClass().getSimpleName());
			if (!authContext.getAuthnContext().isAuthenticated())
				throw new ServiceException(new Result(Status.AUTHN_ERROR, "Not authenticated"));
			if (!authContext.isAuthorized())
				throw new ServiceException(new Result(Status.AUTHZ_ERROR, "Not authorized"));
			execute(authContext, requestObj, response, outputStream);
		} catch (DeserializerException dEx) {
			EcosystemGuardLogger.logError(dEx, this.getClass());
			writeErrorResponse(new Result(Status.CLIENT_ERROR, dEx.getMessage()), HttpServletResponse.SC_BAD_REQUEST,
					response, outputStream);
		} catch (ServiceException sEx) {
			EcosystemGuardLogger.logError(sEx, this.getClass());
			writeErrorResponse(sEx.getResult(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, outputStream);
		} catch (Exception e) {
			EcosystemGuardLogger.logError(e, this.getClass());
			writeErrorResponse(new Result(Status.SERVER_ERROR, e.getMessage()),
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, outputStream);
		}
	}

	private void writeErrorResponse(Result result, int httpStatus, HttpServletResponse response, OutputStream outStream) throws IOException {
		try {
			response.setStatus(httpStatus);
			OutputStreamWriter writer = new OutputStreamWriter(outStream);
			Serializer.serialize(result, Result.class, writer );
		} catch (Exception e) {
			throw new IOException("AuthenticatedRawPostService::writeErrorResponse() error", e);
		}
	}

	/**
	 * Método para la ejecución de la operación del servlet. IMPORTANTE: La
	 * respuesta es obligación del método execute() pero en caso de error
	 * deberá
	 * 
	 * @param authContext
	 * @param request
	 * @param httpResponse
	 * @throws Exception
	 */
	protected abstract void execute(AuthorizationContext authContext, T request, HttpServletResponse httpResponse, OutputStream outStream)
			throws Exception;

	/**
	 * Devuelve la clase Java que modela el XML de la peticion mediante Jaxb
	 * 
	 * @return
	 */
	protected abstract Class<T> getRequestJaxbClass();
}
