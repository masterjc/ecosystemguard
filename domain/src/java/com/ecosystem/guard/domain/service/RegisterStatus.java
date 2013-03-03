/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import com.ecosystem.guard.domain.ServiceStatus;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlType(name = "AppStatus")
@XmlEnum
public enum RegisterStatus implements ServiceStatus {
	@XmlEnumValue("MISSING_ACCOUNTINFO")
	MISSING_ACCOUNTINFO( "MISSING_ACCOUNTINFO" ),
	
	@XmlEnumValue("ALREADY_REGISTERED")
	ALREADY_REGISTERED( "ALREADY_REGISTERED" );
	
	private String statusCode;
	
	private RegisterStatus( String status ) {
		this.statusCode = status;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
