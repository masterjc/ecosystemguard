package com.ecosystem.guard.persistence;

import javax.persistence.EntityManager;

public class NonPersistedTransactionFactory implements TransactionFactory {

	@Override
	public Transaction createTransaction(EntityManager entityManager) throws Exception {
		return new NonPersistedTransaction();
	}

}
