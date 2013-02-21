package com.ecosystem.guard.persistence;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ecosystem.guard.persistence.dao.AccountInfo;

public class PersistenceService {
	private EntityManager entityManager;

	public PersistenceService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void insert(Object object) throws Exception {
		this.entityManager.persist(object);
		this.entityManager.flush();
	}

	/**
	 * @param username
	 *            username to query
	 * @return null if account does not exist
	 * @throws Exception
	 */
	public AccountInfo getAccountInfo(String username) throws Exception {
		TypedQuery<AccountInfo> query = entityManager.createQuery(
				"SELECT a FROM AccountInfo a WHERE a.username = '" + username
						+ "'", AccountInfo.class);
		return query.getSingleResult();
	}
}
