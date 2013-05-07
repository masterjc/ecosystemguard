/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.pc;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ClientConstants {
	public static String ECOSYSTEM_SERVER = "localhost:8080";
	// public static String ECOSYSTEM_SERVER = "registry-ecosystemguard.rhcloud.com";
	public static String ECOSYSTEM_BASE_URL = "http://" + ECOSYSTEM_SERVER + "/ecosystemguard-registry/";

	public static String GET_IP_SERVICE = ECOSYSTEM_BASE_URL + "getip";
	public static String GET_HOSTS_SERVICE = ECOSYSTEM_BASE_URL + "gethosts";
}
