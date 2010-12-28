/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PasswordAuthentication implements Authentication {
	private final String name;
	private final List<String> authorities;
	private final String encodedPassword;

	public PasswordAuthentication(final String name, final String encodedPassword, final List<String> authorities) {
		this.name = name;
		this.authorities = authorities;
		this.encodedPassword = encodedPassword;
	}

	public PasswordAuthentication(final String name, final String encodedPassword, final String... authorities) {
		this(name, encodedPassword, CollectionUtils.newList(authorities));
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return encodedPassword;
	}

	public List<String> getAuthorities() {
		return authorities;
	}
}
