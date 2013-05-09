package com.ecosystem.guard.pc;

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