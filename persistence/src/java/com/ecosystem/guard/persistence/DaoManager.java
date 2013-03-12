package com.ecosystem.guard.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ecosystem.guard.persistence.dao.AccountInfo;
import com.ecosystem.guard.persistence.dao.HostInfo;
import com.ecosystem.guard.persistence.dao.IpInfo;

/**
 * Gestiona el acceso a los objetos de base de datos. Solo tiene efecto sobre la base de datos si la
 * transacci�n est� activa. Mediante la clase @see Transaction se pueden crear instancias de
 * DaoManager
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class DaoManager {
	private EntityManager entityManager;

	public DaoManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Inserta cualquier objeto modelado en la base de datos EcosystemGuard
	 * 
	 * @param object Objeto a insertar en la base de datos
	 * @throws Exception
	 */
	public void insert(Object object) throws Exception {
		this.entityManager.persist(object);
	}

	/**
	 * Actualiza cualquier objeto modelado en la base de datos EcosystemGuard
	 * 
	 * @param object Objeto a actualizar en la base de datos
	 * @throws Exception
	 */
	public void update(Object object) throws Exception {
		this.entityManager.merge(object);
	}

	/**
	 * Elimina un objeto modelado en la base de datos EcosystemGuard
	 * 
	 * @param object Objeto a actualizar en la base de datos
	 * @throws Exception
	 */
	public void delete(Object object) throws Exception {
		this.entityManager.remove(object);
	}

	/**
	 * Busca la información de cuenta de un usuario
	 * 
	 * @param username El identificador de usuario
	 * @return La información de la cuenta. Null si no existe la cuenta.
	 * @throws Exception
	 */
	public AccountInfo getAccountInfo(String username) throws Exception {
		try {
			TypedQuery<AccountInfo> query = entityManager.createQuery(
					"SELECT a FROM AccountInfo a WHERE a.username = '" + username + "'", AccountInfo.class);
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Busca la información de registro del host de un usuario
	 * 
	 * @param username El identificador de usuario
	 * @param username El identificador de host
	 * @return La información del host. Null si no existe el host para el usuario.
	 * @throws Exception
	 */
	public HostInfo getHostInfo(String username, String hostId) throws Exception {
		try {
			TypedQuery<HostInfo> query = entityManager.createQuery("SELECT a FROM HostInfo a WHERE a.username = '"
					+ username + "' AND a.hostId='" + hostId + "'", HostInfo.class);
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Busca la información de la IP registrada para un host
	 * 
	 * @param hostId El identificador de host
	 * @return La información de ip registrada para el host. Null si no existe el host.
	 * @throws Exception
	 */
	public IpInfo getIpInfo(String hostId) throws Exception {
		try {
			TypedQuery<IpInfo> query = entityManager.createQuery("SELECT a FROM IpInfo a WHERE a.hostId = '" + hostId
					+ "'", IpInfo.class);
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Busca los hosts registrados de un usuario
	 * 
	 * @param username El identificador de usuario
	 * @return La información del host. Null si no existe el host para el usuario.
	 * @throws Exception
	 */
	public List<HostInfo> getHostsInfo(String username) throws Exception {
		try {
			TypedQuery<HostInfo> query = entityManager.createQuery("SELECT a FROM HostInfo a WHERE a.username = '"
					+ username + "'", HostInfo.class);
			return query.getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Borra la cuenta de un usuario. Retorna null si no existe el usuario a borrar. Retorna la
	 * informaci�n del usuario eliminado si se consigue eliminar correctamente
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public AccountInfo deleteAccount(String username) throws Exception {
		AccountInfo info = getAccountInfo(username);
		if (info == null)
			return null;
		entityManager.remove(info);
		return info;
	}
}
