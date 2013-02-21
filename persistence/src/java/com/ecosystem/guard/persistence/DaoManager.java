package com.ecosystem.guard.persistence;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ecosystem.guard.persistence.dao.AccountInfo;

/**
 * Gestiona el acceso a los objetos de base de datos
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class DaoManager {
	private EntityManager entityManager;

	public DaoManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}
	
	public void rollbackTransaction() {
		entityManager.getTransaction().rollback();
	}
	
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}

	/**
	 * Inserta cualquier objeto modelado en la base de datos EcosystemGuard
	 * 
	 * @param object Objeto a insertar en la base de datos
	 * @throws Exception
	 */
	public void insert(Object object) throws Exception {
		this.entityManager.persist(object);
		this.entityManager.flush();
	}

	/**
	 * Busca la información de cuenta de un usuario
	 * 
	 * @param username El identificador de usuario
	 * @return La información de la cuenta. Null si no existe la cuenta.
	 * @throws Exception
	 */
	public AccountInfo getAccountInfo(String username) throws Exception {
		TypedQuery<AccountInfo> query = entityManager.createQuery("SELECT a FROM AccountInfo a WHERE a.username = '"
				+ username + "'", AccountInfo.class);
		return query.getSingleResult();
	}
}
