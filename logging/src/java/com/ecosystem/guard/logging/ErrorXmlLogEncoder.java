/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.logging.Error;
import com.ecosystem.guard.domain.logging.Parameter;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ErrorXmlLogEncoder {
	public static String encode(String message, Exception e, List<Parameter> parameters) throws Exception {
		StringWriter str = new StringWriter();
		PrintWriter writer = new PrintWriter(str);
		e.printStackTrace(writer);
		writer.close();
		Error error = new Error();
		error.setException(Base64.encodeBase64String(str.toString().getBytes()));
		error.setMessage(message);
		error.setParameters(parameters);
		return Serializer.serialize(error, Error.class);
	}
}
