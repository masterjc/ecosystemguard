/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.nosql.type;

import java.io.IOException;

/**
 * @version $Revision$
 */
public class DoubleConverter implements EntryTypeConverter {

	@Override
	public Object deserialize(byte[] input) throws IOException {
		return new Double(new String(input));
	}

	@Override
	public byte[] serialize(Object input) throws Exception {
		Double d = (Double) input;
		return d.toString().getBytes();
	}
}
