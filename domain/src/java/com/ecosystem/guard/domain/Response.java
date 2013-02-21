/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Result.Status;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class Response<T extends ServiceStatus> {

	private Result<T> result;
	
	public Response() {
	}
	
	public Response(Status status) {
		Result<T> result = new Result<T>();
		result.setStatus(status);
		setResult(result);
	}
	
	public Response(Status status, T appStatus, String message ) {
		Result<T> result = new Result<T>();
		result.setStatus(status);
		result.setAppStatus(appStatus);
		result.setMessage(message);
		setResult(result);
	}
	
	public Result<T> getResult() {
		return result;
	}
	
	@XmlElement
	public void setResult(Result<T> result) {
		this.result = result;
	}

}
