package com.example.taskapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private final String secretKey;

    private final long expirationTime;

    public JwtUtil(@Value("${jwt.secretKey}") String secretKey, @Value("${jwt.expirationTime}") long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(expirationTime, ChronoUnit.SECONDS);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getDecodedJWT(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }
}
