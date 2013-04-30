/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.phidgets.exceptions;

/**
 * El sensor especificado no está conectado o no está disponible
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class NotAttachedSensorException extends Exception {
	private static final long serialVersionUID = 387674214404621430L;
	
	private String sensorName;

	public NotAttachedSensorException(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorName() {
		return sensorName;
	}

}
