package com.ecosystem.guard.persistence;

import java.math.BigInteger;
import java.security.SecureRandom;

public class NonPersistedTransaction implements Transaction {
	private static final int RADIX = 32;
	private static final int NUM_BITS = 130;
	
	private String id;
	
	NonPersistedTransaction() {
		SecureRandom secureRandom = new SecureRandom();
		id = (new BigInteger(NUM_BITS, secureRandom)).toString(RADIX);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void beginTransaction() {
	}

	@Override
	public void commitTransaction() {
	}

	@Override
	public void rollbackTransaction() {
	}

}
