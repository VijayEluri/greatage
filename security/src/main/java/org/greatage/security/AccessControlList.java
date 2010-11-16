/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccessControlList {

	Object getSecuredObject();

	AccessControlEntry getAccessControlEntry(String authority, String permission);

}