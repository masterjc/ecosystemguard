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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServlet;

/**
 * Servlet con inyección del EntityManagerFactory con gestión de transacciones
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
public class PersistenceHttpServlet extends HttpServlet {

	@PersistenceUnit(name = "EcosystemGuard")
	private EntityManagerFactory entityManagerFactory;

	public EntityManager createEntityManager() {
		if (entityManagerFactory == null)
			return null;
		return entityManagerFactory.createEntityManager();
	}

}
