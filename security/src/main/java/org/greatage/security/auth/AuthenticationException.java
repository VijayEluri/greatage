package org.greatage.security.auth;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthenticationException extends SecurityException {

	public AuthenticationException() {
	}

	public AuthenticationException(final String s) {
		super(s);
	}

	public AuthenticationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(final Throwable cause) {
		super(cause);
	}
}
