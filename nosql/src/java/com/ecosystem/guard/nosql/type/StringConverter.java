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

/**
 * @version $Revision$
 */
public class StringConverter implements EntryTypeConverter {

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.nosql.type.EntryTypeConverter#deserialize(byte[])
	 */
	@Override
	public Object deserialize(byte[] input) throws Exception {
		return new String(input);
	}

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.nosql.type.EntryTypeConverter#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Object input) throws Exception {
		String in = (String)input;
		return in.getBytes();
	}

}
