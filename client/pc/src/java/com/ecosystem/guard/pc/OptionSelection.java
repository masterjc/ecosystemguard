package com.ecosystem.guard.pc;

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