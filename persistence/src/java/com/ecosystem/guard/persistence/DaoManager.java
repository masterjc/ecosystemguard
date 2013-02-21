package com.ecosystem.guard.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ecosystem.guard.persistence.dao.AccountInfo;

/**
 * Gestiona el acceso a los objetos de base de datos. Solo tiene efecto sobre la
 * base de datos si la transacci�n est� activa. Mediante la clase @see
 * Transaction se pueden crear instancias de DaoManager
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class DaoManager {
	private EntityManager entityManager;

	DaoManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Inserta cualquier objeto modelado en la base de datos EcosystemGuard
	 * 
	 * @param object
	 *            Objeto a insertar en la base de datos
	 * @throws Exception
	 */
	public void insert(Object object) throws Exception {
		this.entityManager.persist(object);
		this.entityManager.flush();
	}

	/**
	 * Busca la información de cuenta de un usuario
	 * 
	 * @param username
	 *            El identificador de usuario
	 * @return La información de la cuenta. Null si no existe la cuenta.
	 * @throws Exception
	 */
	public AccountInfo getAccountInfo(String username) throws Exception {
		try {
			TypedQuery<AccountInfo> query = entityManager.createQuery(
					"SELECT a FROM AccountInfo a WHERE a.username = '" + username
							+ "'", AccountInfo.class);
			return query.getSingleResult();
		} catch( NoResultException e ) {
			return null;
		}
	}
}
