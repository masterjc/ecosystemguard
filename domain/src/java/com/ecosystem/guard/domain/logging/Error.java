/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.logging;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class Error {
	private String message;
	private String exception;
	private List<Parameter> parameters;

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

	public String getException() {
		return exception;
	}

	@XmlElement
	public void setException(String exception) {
		this.exception = exception;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	@XmlElement(name = "parameter")
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
