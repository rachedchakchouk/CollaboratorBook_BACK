package com.ditriot.securityms.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;

/**
 * Single source of the HMAC key used to sign and verify JWTs.
 * The key is read from the JWT_SECRET environment variable (at least 32 characters)
 * so that it is never committed to the repository.
 */
public final class JwtAlgorithm {

    private static final int MIN_SECRET_LENGTH = 32;

    private JwtAlgorithm() {
    }

    public static Algorithm get() {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < MIN_SECRET_LENGTH) {
            throw new IllegalStateException(
                    "JWT_SECRET environment variable is missing or shorter than " + MIN_SECRET_LENGTH + " characters");
        }
        return Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
    }
}
