/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.exceptions;

/**
 * Error deserializando un XML en un objeto de dominio
 *  
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public class DeserializerException extends Exception {
	public DeserializerException() {
		super();
	}

	public DeserializerException(String message) {
		super(message);
	}
	
	public DeserializerException(Throwable t) {
		super(t);
	}

	public DeserializerException(String message, Throwable t) {
		super(message, t);
	}
}
