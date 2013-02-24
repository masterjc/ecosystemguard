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


/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface Transaction {
	public String getId();
	public boolean isActive();
	public void beginTransaction();
	public void commitTransaction();
	public void rollbackTransaction();
}
