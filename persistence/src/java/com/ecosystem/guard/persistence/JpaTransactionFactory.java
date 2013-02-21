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

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class JpaTransactionFactory implements TransactionFactory {

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.persistence.TransactionFactory#createTransaction(javax.persistence.EntityManager)
	 */
	@Override
	public Transaction createTransaction(EntityManager entityManager) throws Exception {
		return new JpaTransaction(entityManager);
	}

	

}
