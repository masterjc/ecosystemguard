/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.persistence;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.persistence.EntityManager;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class JpaTransaction implements Transaction {

	private static final int RADIX = 32;
	private static final int NUM_BITS = 130;

	private String id;
	private EntityManager entityManager;

	public JpaTransaction(EntityManager entityManager) {
		this.entityManager = entityManager;
		SecureRandom secureRandom = new SecureRandom();
		id = (new BigInteger(NUM_BITS, secureRandom)).toString(RADIX);
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean isActive() {
		return entityManager.getTransaction().isActive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.persistence.Transaction#beginTransaction()
	 */
	@Override
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.persistence.Transaction#commitTransaction()
	 */
	@Override
	public void commitTransaction() {
		if (isActive()) {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.persistence.Transaction#rollbackTransaction()
	 */
	@Override
	public void rollbackTransaction() {
		if (isActive()) {
			entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}

}
