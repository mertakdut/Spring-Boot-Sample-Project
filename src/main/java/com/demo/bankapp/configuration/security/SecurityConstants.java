package com.demo.bankapp.configuration.security;

final class SecurityConstants {

	private SecurityConstants() {
	}

	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}
