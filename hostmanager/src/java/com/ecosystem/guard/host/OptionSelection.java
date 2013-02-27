/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class OptionSelection<T> {
	private int optionNumber;
	private T function;

	public OptionSelection(int optionNumber, T function) {
		this.optionNumber = optionNumber;
		this.function = function;
	}

	public int getOptionNumber() {
		return optionNumber;
	}

	public T getFunction() {
		return function;
	}
}