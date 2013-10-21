/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ManagerConstants {
	public static String ECOSYSTEM_REGISTRY_SERVER = "registry:8080";
	public static String EXTERNAL_WEB = "google.com";
	// public static String ECOSYSTEM_SERVER = "registry-ecosystemguard.rhcloud.com";
	public static String ECOSYSTEM_BASE_URL = "http://" + ECOSYSTEM_REGISTRY_SERVER + "/ecosystemguard-registry/";

	public static String REGISTER_SERVICE = ECOSYSTEM_BASE_URL + "register";
	public static String UNREGISTER_SERVICE = ECOSYSTEM_BASE_URL + "unregister";
	public static String UPDATE_CREDENTIALS_SERVICE = ECOSYSTEM_BASE_URL + "updatecredentials";
	public static String REGISTER_HOST_SERVICE = ECOSYSTEM_BASE_URL + "registerhost";
	public static String UNREGISTER_HOST_SERVICE = ECOSYSTEM_BASE_URL + "unregisterhost";
	public static String GET_IP_SERVICE = ECOSYSTEM_BASE_URL + "getip";
	public static String UPDATE_IP_SERVICE = ECOSYSTEM_BASE_URL + "updateip";
}
