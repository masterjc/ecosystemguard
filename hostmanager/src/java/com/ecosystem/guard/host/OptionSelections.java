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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class OptionSelections<T> {
	private List<OptionSelection<T>> options = new ArrayList<OptionSelection<T>>();

	public void add(OptionSelection<T> selection) {
		options.add(selection);
	}

	public T getSelection(int numericSelection) {
		for (OptionSelection<T> option : options) {
			if (option.getOptionNumber() == numericSelection)
				return option.getFunction();
		}
		return null;
	}
}