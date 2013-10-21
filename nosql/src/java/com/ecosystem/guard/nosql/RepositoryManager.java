/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.nosql;

import org.joda.time.DateTime;

/**
 * @version $Revision$
 */
public interface RepositoryManager {
	<T> void insert( String entry, T value ) throws Exception;
	<T> void insert( String entry, DateTime date, T value) throws Exception;
	<T> T get( String entry, DateTime date ) throws Exception;
	<T> T getLast( String entry ) throws Exception;
	<T> T getFirst( String entry ) throws Exception;
	<T> T getMax( String entry ) throws Exception;
	<T> T getMin( String entry ) throws Exception;
}
